package hello.advanced.app.v0;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class OrderRepositoryV0Test {

    @Autowired
    OrderRepositoryV0 repository;

    @Test
    void saveTest() {
        Assertions.assertTimeout(
                Duration.ofMillis(1500), () -> {
                    repository.save("itemId");
                });
    }

    @Test
    void saveExceptionTest() {
        String itemId = "ex";
        assertThatThrownBy(() -> repository.save(itemId))
                .isInstanceOf(IllegalStateException.class);
    }
}
