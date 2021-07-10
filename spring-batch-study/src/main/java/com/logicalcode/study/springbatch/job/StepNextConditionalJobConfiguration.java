package com.logicalcode.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Step 1 실행 후 정상일 때는 Step 2 를, 오류가 났을 때는 Step 3 를 수행
 *
 *
 * .on() : 캐치할 ExitStatus 지정
 * .to() : 다음으로 이동한 Step 지정
 * .from()
 *      - 일종의 이벤트 리스너 역할
 *      - 상태값을 보고 일치하는 상태라면 to()에 포함된 step을 호출
 *      - step1의 이벤트 캐치가 FAILED로 되있는 상태에서 추가로 이벤트 캐치하려면 from()을 사용해야함
 * .end()
 *      - FlowBuilder를 반환하는 end와 FlowBuilder를 종료하는 end 2개가 있음
 *      - FlowBuilder를 반환하는 end 사용 시 계속해서 from을 이어갈 수 있음
 *
 *
 * BatchStatus
 *      - Job 또는 Step 의 실행 결과를 Spring 에서 기록할 때 사용하는 Enum
 *
 * ExitStatus
 *      - Step의 실행 후 상태
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static String ALL = "*";

    @Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get("stepNextConditionalJob")
                .start(conditionalStep1())              // Step1이
                    .on(ExitStatus.FAILED.getExitCode())// 실패할 경우
                    .to(conditionalStep3())             // Step3으로 이동하는데
                    .on(ALL)                            // Step3의 결과와 상관없이
                    .end()                              // Flow를 종료한다(FlowBuilder를 반환)

                .from(conditionalStep1())               // Step1이
                    .on(ALL)                            // 앞서 선언한 결과 외 모든 결과에서
                    .to(conditionalStep2())             // Step2로 이동하고
                    .next(conditionalStep3())           // Step2가 정상 종료시 Step3으로 이동
                    .on(ALL)                            // Step3의 결과와 상관없이
                    .end()                              // Flow를 종료한다(FlowBuilder를 반환)

                .end()                                  // Job 종료(FlowBuilder를 종료)
                .build();
    }

    @Bean
    public Step conditionalStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>> This is stepNextConditionalStep1");
                    /**
                     * ExitStatus 를 FAILED 로 지정해 오류가 발생했음을 알린다.
                     * 해당 Status 를 보고 flow 가 진행된다.
                     */
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalStep2() {
        return stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>> This is stepNextConditionalStep2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step conditionalStep3() {
        return stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>> This is stepNextConditionalStep3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
