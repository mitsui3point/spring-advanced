package hello.advanced.trace.hellotrace;

import ch.qos.logback.classic.spi.ILoggingEvent;
import hello.advanced.log.appender.LogAppender;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTraceV2Test extends LogAppender {

    @Test
    @DisplayName("로그 시작 종료")
    void startEnd() {
        //given
        HelloTraceV2 helloTraceV2 = new HelloTraceV2();
        String message1 = "message1";
        String message2 = "message2";
        //when
        TraceStatus status1 = helloTraceV2.start(message1);
        TraceStatus status2 = helloTraceV2.startSync(status1.getTraceId(), message2);
        helloTraceV2.end(status2);
        helloTraceV2.end(status1);
        //then
        Optional<ILoggingEvent> optionalMessage = listAppender.list
                .stream()
                .filter(o -> o.getFormattedMessage()
                        .contains("|<--message2"))
                .findAny();
        assertThat(optionalMessage).isPresent();
    }

    @Test
    @DisplayName("로그 시작 예외")
    void startException() {
        //given
        HelloTraceV2 helloTraceV2 = new HelloTraceV2();
        String message1 = "message1";
        String message2 = "message2";
        //when
        TraceStatus status1 = helloTraceV2.start(message1);
        TraceStatus status2 = helloTraceV2.startSync(status1.getTraceId(), message2);
        helloTraceV2.exception(status2, new RuntimeException("예외 발생"));
        helloTraceV2.exception(status1, new RuntimeException("예외 발생"));
        //then
        Optional<ILoggingEvent> optionalMessage = listAppender.list
                .stream()
                .filter(o -> o.getFormattedMessage()
                        .contains("RuntimeException"))
                .filter(o -> o.getFormattedMessage()
                        .contains("|<X-message2"))
                .findAny();
        assertThat(optionalMessage).isPresent();
    }
}
