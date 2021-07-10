package com.logicalcode.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;


/**
 * Step의 결과에 따라 다른 Step으로 이동하는 방식의 문제점
 * 1. Step이 담당하는 역할이 2개 이상이 됨
 *      - 실제 해당 Step이 처리해야할 로직 외에도 분기처리를 위한 ExitStatus 조작이 필요
 * 2. 다양한 분기 로직 처리의 어려움
 *      - ExitStatus를 커스텀하게 고치기 위해선 Listener를 생성하고 Job Flow에 등록하는 번거로움이 존재
 *
 *
 * JobExecutionDecider
 *      - Spring Batch에서 Step들의 flow 속에서 분기만 담당하는 타입
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final String ODD = "ODD";
    private static final String EVEN = "EVEN";


    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(startStep()) // 첫번째 Step 수행
                .next(decider())    // 홀수/짝수 구분
                .from(decider())
                    .on(ODD)        // decider 결과가 홀수라면
                    .to(oddStep())  // oddStep 수행
                .from(decider())
                    .on(EVEN)       // decider 결과가 짝수라면
                    .to(evenStep()) // evenStep 수행
                .end()
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>> Start Step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>> Even Step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>> Odd Step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    /**
     * 분기 로직에 대한 모든 처리를 담당함
     */
    public static class OddDecider implements JobExecutionDecider {
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random random = new Random();
            int randomNumber = random.nextInt(50) + 1;
            log.info("랜덤숫자: {}", randomNumber);
            if(randomNumber % 2 == 0) {
                return new FlowExecutionStatus(EVEN);
            } else {
                return new FlowExecutionStatus(ODD);
            }
        }
    }
}
