package hello.advanced.app.v5;

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
public class OrderServiceV5Test extends LogAppender {

    @Autowired
    OrderServiceV5 orderService;

    @Test
    @DisplayName("주문을 성공한다")
    void orderItemTest() {
        assertTimeout(Duration.ofMillis(1500), () -> {
            orderService.orderItem("itemId");
        });
    }

    @Test
    @DisplayName("주문을 실패한다")
    void orderItemExceptionTest() {
        String itemId = "ex";
        assertThatThrownBy(() -> orderService.orderItem(itemId))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 성공시 로그를 남긴다")
    void orderItemLogTest() {
        //given
        String expectedLog = "OrderService.orderItem";
        //when
        orderService.orderItem("itemId");
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }

    @Test
    @DisplayName("주문 실패시 로그를 남긴다")
    void orderItemExceptionLogTest() {
        //given
        String expectedLog = "OrderService.orderItem";
        String itemId = "ex";
        //when
        assertThatThrownBy(() -> orderService.orderItem(itemId))
                .isInstanceOf(IllegalStateException.class);
        //then
        assertThat(getContainsLog(expectedLog)).isPresent();
    }
}
