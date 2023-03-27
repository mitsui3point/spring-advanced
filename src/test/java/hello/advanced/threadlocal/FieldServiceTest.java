package hello.advanced.threadlocal;

import hello.advanced.log.appender.LogAppender;
import hello.advanced.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class FieldServiceTest extends LogAppender {
    private FieldService service;

//    @BeforeEach
//    void setUp() {
//        service = new FieldService();
//    }

    @Test
    @DisplayName("ThreadA 저장 -> ThreadA 조회 -> ThreadB 저장 -> ThreadB 조회 순서로 동작한다")
    void field() {
        service = new FieldService();
        log.info("main start");
        Runnable userA = () -> service.logic("userA");
        Runnable userB = () -> service.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(2000);//동시성 문제 발생 X
        threadB.start();
        sleep(3000);//메인 쓰레드 종료 대기; CountDownLatch 로 대체가능
        log.info("main exit");

        assertThat(getOrderedLogs())
                .containsExactlyInAnyOrder(
                        "[INFO] 저장 name=userA -> nameStore=null",
                        "[INFO] 조회 nameStore=userA",
                        "[INFO] 저장 name=userB -> nameStore=userA",
                        "[INFO] 조회 nameStore=userB"
                );

    }

    @Test
    @DisplayName("ThreadA 저장 -> ThreadB 저장 -> ThreadA 조회 -> ThreadB 조회 순서로 동작한다")
    void fieldReadBeforeSave() {
        service = new FieldService();
        log.info("main start");
        Runnable userA = () -> service.logic("userA");
        Runnable userB = () -> service.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);//동시성 문제 발생
        threadB.start();
        sleep(3000);//메인 쓰레드 종료 대기; CountDownLatch 로 대체가능
        log.info("main exit");

        assertThat(getOrderedLogs())
                .containsExactlyInAnyOrder(
                        "[INFO] 저장 name=userA -> nameStore=null",
                        "[INFO] 저장 name=userB -> nameStore=userA",
                        "[INFO] 조회 nameStore=userB",
                        "[INFO] 조회 nameStore=userB"
                );
    }

    @Test
    @DisplayName("서비스의 로직이 1초 후 실행된다")
    void logic() {
        service = new FieldService();
        //given
        String testName = "testName";
        //then
        assertTimeoutPreemptively(Duration.ofMillis(1300), () -> {
            //when
            String nameStore = service.logic(testName);
            assertThat(nameStore).isEqualTo(testName);
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
