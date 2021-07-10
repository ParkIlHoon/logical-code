package com.logicalcode.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .build();
    }

    /**
     * BATCH_JOB_EXECUTION 에는 JOB_INSTANCE 의 모든 내역(성공/실패)을 가지고 있음
     *
     *
     * @param requestDate Spring Batch 가 실행될 때 외부에서 받을 수 있는 파라미터
     *                    같은 Batch Job 이라도 Job Parameter 가 다르면 Batch_JOB_INSTANCE 테이블에 Job Instance 가 추가된다.
     *                    Job Parameter 가 동일하면 JobInstanceAlreadyCompleteException 이 발생함.
     *
     *                    동일한 Job 이 Job Parameter 가 달라지면 그때마다 BATCH_JOB_INSTANCE 에 생성되며, 동일한 Job Parameter 는 여러개 존재할 수 없음
     * @return
     */
    @Bean
    @JobScope
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> {
                    throw new IllegalArgumentException("step1 에서 실패했습니다.");
                })
                .build();
    }


    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Step2");
                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
