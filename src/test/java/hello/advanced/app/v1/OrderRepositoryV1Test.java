package hello.advanced.app.v1;

import hello.advanced.log.appender.LogAppender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@SpringBootTest
public class OrderRepositoryV1Test extends LogAppender {

    @Autowired
    OrderRepositoryV1 repository;

    @Test
    @DisplayName("주문정보 저장에 성공한다")
    void saveTest() {
        assertTimeout(
                Duration.ofMillis(1500), () -> {
                    repository.save("itemId");
                });
    }

    @Test
    @DisplayName("주문정보 저장에 실패한다")
    void saveExceptionTest() {
        String itemId = "ex";
        assertThatThrownBy(() -> repository.save(itemId))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문정보 저장 성공 로그를 출력한다")
    void saveLogTest() {
        //given
        String expectedLog = "OrderRepository.save()";
        //when
        repository.save("itemId");
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }

    @Test
    @DisplayName("주문정보 저장 실패 로그를 출력한다")
    void saveExceptionLogTest() {
        //given
        String expectedLog = "IllegalStateException";
        String itemId = "ex";
        //when
        assertThatThrownBy(() -> repository.save(itemId))
                .isInstanceOf(IllegalStateException.class);
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }
}
