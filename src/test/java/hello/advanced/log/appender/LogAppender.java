package hello.advanced.log.appender;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import hello.advanced.logtrace.FieldLogTrace;
import hello.advanced.threadlocal.code.FieldService;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.hasText;

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
        if (className.contains("V2")) {
            logger = loggerContext.getLogger(HelloTraceV2.class);
        }
        if (className.contains("FieldLogTrace") || className.contains("V3")) {
            logger = loggerContext.getLogger(FieldLogTrace.class);
        }
        if (className.contains("FieldService")) {
            logger = loggerContext.getLogger(FieldService.class);
        }
        if (logger == null) {
            throw new IllegalArgumentException("LogAppender 에서 지원되지 않는 클래스입니다.");
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
                .filter(o -> {
                    if (o != null) return o.getFormattedMessage().contains(expectedLog);
                    return false;
                })
                .findAny();
    }

    protected List<String> getOrderedLogs() {
        if (ObjectUtils.isEmpty(listAppender.list)) {
            return null;
        }
        List<String> logs = listAppender.list
                .stream()
                .map(o -> o.toString())
                .collect(Collectors.toList());
        return logs;
    }

}
