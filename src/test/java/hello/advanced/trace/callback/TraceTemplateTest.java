package hello.advanced.trace.callback;

import hello.advanced.log.appender.LogAppender;
import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class TraceTemplateTest extends LogAppender {
    @Test
    void traceTemplateTest() {
        TraceTemplate template = new TraceTemplate(new ThreadLocalLogTrace());
        template.execute("test", () -> "ok");

        Assertions.assertThat(getContainsLog("test"));
    }
}
