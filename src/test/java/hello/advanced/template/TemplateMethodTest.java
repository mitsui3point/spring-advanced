package hello.advanced.template;

import hello.advanced.log.appender.LogAppender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class TemplateMethodTest extends LogAppender {

    @Test
    void templateMethod0() {
        //when
        logic1();
        logic2();
        //then
        assertThat(getContainsLog("비즈니스 로직1 실행")).isPresent();
        assertThat(getContainsLog("비즈니스 로직2 실행")).isPresent();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
