package com.hanghae.settlement_system.config.batch;

import com.hanghae.settlement_system.settlement.domain.Settlement;
import com.hanghae.settlement_system.settlement.repository.SettlementRepository;
import com.hanghae.settlement_system.video.domain.VideoViewLog;
import com.hanghae.settlement_system.video.repository.VideoViewLogRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Configuration
public class SettlementJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final VideoViewLogRepository videoViewLogRepository;
    private final SettlementRepository settlementRepository;

    public SettlementJobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, VideoViewLogRepository videoViewLogRepository, SettlementRepository settlementRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.videoViewLogRepository = videoViewLogRepository;
        this.settlementRepository = settlementRepository;
    }

    @Bean
    public Job settlementJob() {
        return new JobBuilder("settlementJob", jobRepository)
                .start(settlementStep())
                .build();
    }

    @Bean
    public Step settlementStep() {
        return new StepBuilder("settlementStep", jobRepository)
                .<VideoViewLog, Settlement>chunk(1, platformTransactionManager)
                .reader(settlementReader(null))
                .processor(settlementProcessor(null))
                .writer(settlementWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<VideoViewLog> settlementReader(@Value("#{jobParameters['userId']}") Long userId) {
        return new RepositoryItemReaderBuilder<VideoViewLog>()
                .name("settlementReader")
                .pageSize(10)
                .methodName("findByUserId")
                .arguments(Collections.singletonList(userId))
                .repository(videoViewLogRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<VideoViewLog, Settlement> settlementProcessor(@Value("#{jobParameters['date']}") String date) {

        return log -> {
            LocalDate settlementDate = LocalDate.parse(date);
            Long userId = log.getUser().getId();

            // Settlement가 이미 존재하는지 확인
            if (settlementRepository.findByUserIdAndSettlementDate(userId, settlementDate).isPresent()) {
                return null;
            }

            Long viewCount = videoViewLogRepository.countByUserId(userId);
            return new Settlement(userId, settlementDate, viewCount.doubleValue(), 0.0);
        };
    }

    @Bean
    public RepositoryItemWriter<Settlement> settlementWriter() {
        return new RepositoryItemWriterBuilder<Settlement>()
                .repository(settlementRepository)
                .methodName("save")
                .build();
    }
}
