package hello.advanced.trace.template;

import hello.advanced.log.appender.LogAppender;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AbstractTemplateTest extends LogAppender {
    @Test
    void abstractTemplateTest() {
        AbstractTemplate<Void> template = new AbstractTemplate(new ThreadLocalLogTrace()) {
            @Override
            public Void call() {
                log.info("call method");
                return null;
            }
        };
        template.execute("execute!@");

        assertThat(getContainsLog("call method")).isPresent();
    }
}
