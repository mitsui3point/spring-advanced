package hello.advanced.trace.hellotrace;

import ch.qos.logback.classic.spi.ILoggingEvent;
import hello.advanced.log.appender.LogAppender;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTraceV1Test extends LogAppender {

    @Test
    @DisplayName("로그 시작 종료")
    void startEnd() {
        //given
        HelloTraceV1 helloTraceV1 = new HelloTraceV1();
        String message = "message";
        //when
        TraceStatus status = helloTraceV1.start(message);
        helloTraceV1.end(status);
        //then
        Optional<ILoggingEvent> optionalMessage = listAppender.list
                .stream()
                .filter(o -> o.getFormattedMessage()
                        .contains("message"))
                .findAny();
        assertThat(optionalMessage).isPresent();
    }

    @Test
    @DisplayName("로그 시작 예외")
    void startException() {
        //given
        HelloTraceV1 helloTraceV1 = new HelloTraceV1();
        String message = "message";
        //when
        TraceStatus status = helloTraceV1.start(message);
        helloTraceV1.exception(status, new RuntimeException("예외 발생"));
        //then
        Optional<ILoggingEvent> optionalMessage = listAppender.list
                .stream()
                .filter(o -> o.getFormattedMessage()
                        .contains("RuntimeException"))
                .findAny();
        assertThat(optionalMessage).isPresent();
    }
}
