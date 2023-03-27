package hello.advanced.threadlocal;

import hello.advanced.log.appender.LogAppender;
import hello.advanced.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ThreadLocalServiceTest extends LogAppender {

    private ThreadLocalService service;

    @Test
    @DisplayName("저장 순서 상관없이 nameStore 에 userA, userB 가 저장된다")
    void fieldReadBeforeSave() {
        service = new ThreadLocalService();
        log.info("main start");
        Runnable userA = () -> service.logic("userA");
        Runnable userB = () -> service.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);//동시성 문제 발생 X
        threadB.start();
        sleep(3000);//메인 쓰레드 종료 대기; CountDownLatch 로 대체가능
        log.info("main exit");

        assertThat(getOrderedLogs())
                .contains(
                        "[INFO] 저장 name=userA -> nameStore=null",
                        "[INFO] 저장 name=userB -> nameStore=null",
                        "[INFO] 조회 nameStore=userA",
                        "[INFO] 조회 nameStore=userB"
                );
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
