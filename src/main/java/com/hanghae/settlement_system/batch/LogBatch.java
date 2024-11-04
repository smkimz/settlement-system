package com.hanghae.settlement_system.batch;

import com.hanghae.settlement_system.settlement.domain.Settlement;
import com.hanghae.settlement_system.settlement.repository.SettlementRepository;
import com.hanghae.settlement_system.video.domain.VideoViewLog;
import com.hanghae.settlement_system.video.repository.VideoViewLogRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class LogBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final VideoViewLogRepository videoViewLogRepository;
    private final SettlementRepository settlementRepository;

    public LogBatch(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, VideoViewLogRepository videoViewLogRepository, SettlementRepository settlementRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.videoViewLogRepository = videoViewLogRepository;
        this.settlementRepository = settlementRepository;
    }

    @Bean
    public Job settlementJob() {
        return new JobBuilder("settlementJob", jobRepository)
                .start(firstSettlementStep())
                .build();
    }

    @Bean
    public Step firstSettlementStep() {
        return new StepBuilder("firstSettlementStep", jobRepository)
                .<VideoViewLog, Settlement>chunk(10, platformTransactionManager)
                .reader(logReader())
                .processor(settlementProcessor())
                .writer(resultWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<VideoViewLog> logReader() {

        return new RepositoryItemReaderBuilder<VideoViewLog>()
                .name("logReader")
                .pageSize(10)
                .methodName("findAll")
                .repository(videoViewLogRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<VideoViewLog, Settlement> settlementProcessor() {

        return new ItemProcessor<VideoViewLog, Settlement>() {
            @Override
            public Settlement process(VideoViewLog log) throws Exception {
                Settlement settlement = new Settlement();
                settlement.setVideoAmount(log.getVideo().getViewCount()); // 수정
                return settlement;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<Settlement> resultWriter() {

        return new RepositoryItemWriterBuilder<Settlement>()
                .repository(settlementRepository)
                .methodName("save")
                .build();
    }
}
