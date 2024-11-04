package com.hanghae.settlement_system.config.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class BatchController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public BatchController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping("/batch/settlement")
    public String settlementApi(@RequestParam("date") String date, @RequestParam("userId") Long userId) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .addLong("userId", userId)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("settlementJob"), jobParameters);

        return "ok";
    }
}
