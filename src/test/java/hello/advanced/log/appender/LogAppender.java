package hello.advanced.log.appender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LogAppender {
    protected ListAppender<ILoggingEvent> listAppender;
    private Logger logger;
    private LoggerContext loggerContext;

    @BeforeEach
    void setUp() {
        setLogAppenderInfo();
        logAppendStart();
    }

    @AfterEach
    public void tearDown() {
        logAppendEnd();
    }

    private void setLogAppenderInfo() {
        String className = this.getClass().getName();
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        if (className.contains("V1")) {
            logger = loggerContext.getLogger(HelloTraceV1.class);
        }
        listAppender = new ListAppender<>();
    }

    private void logAppendStart() {
        listAppender.start();
        logger.addAppender(listAppender);
    }

    private void logAppendEnd() {
        logger.detachAppender(listAppender);
        listAppender.stop();
    }

    protected Optional<ILoggingEvent> getContainsLog(String expectedLog) {
        return listAppender.list
                .stream()
                .filter(o -> o.getFormattedMessage().contains(expectedLog))
                .findAny();
    }
}
