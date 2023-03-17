package hello.advanced.app.v0;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class OrderServiceV0Test {

    @Autowired
    OrderServiceV0 orderService;

    @Test
    void orderItemTest() {
        Assertions.assertTimeout(Duration.ofMillis(1500), () -> orderService.orderItem("itemId"));
    }


    @Test
    void orderItemExceptionTest() {
        String itemId = "ex";
        assertThatThrownBy(() -> orderService.orderItem(itemId))
                .isInstanceOf(IllegalStateException.class);
    }
}
