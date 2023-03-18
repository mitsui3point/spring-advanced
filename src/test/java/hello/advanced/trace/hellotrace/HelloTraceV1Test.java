package hello.advanced.trace.hellotrace;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloTraceV1Test {

    private Logger logger;
    private LoggerContext loggerContext;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        logger = loggerContext.getLogger(HelloTraceV1.class);
        listAppender = new ListAppender<>();

        listAppender.start();
        logger.addAppender(listAppender);
    }

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

    @AfterEach
    public void tearDown() {
        logger.detachAppender(listAppender);
        listAppender.stop();
    }
}
