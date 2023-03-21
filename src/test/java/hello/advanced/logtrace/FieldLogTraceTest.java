package hello.advanced.logtrace;

import hello.advanced.log.appender.LogAppender;
import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldLogTraceTest extends LogAppender {

    private TraceLog traceLog = new FieldLogTrace();

    @Test
    @DisplayName("깊이 2단계의 로직을 성공한 뒤 로그 출력 한다")
    void traceLogLevel2() {
        TraceStatus status1 = traceLog.begin("message1");
        TraceStatus status2 = traceLog.begin("message2");
        traceLog.end(status2);
        traceLog.end(status1);

        assertThat(getContainsLog("message1")).isPresent();
        assertThat(getContainsLog("|-->message2")).isPresent();
        assertThat(getContainsLog("|<--message2")).isPresent();
        assertThat(getContainsLog("message1")).isPresent();
    }

    @Test
    @DisplayName("깊이 2단계의 로직을 실패한 뒤 로그 출력 한다")
    void traceLogLevel2Fail() {
        TraceStatus status1 = traceLog.begin("message1");
        TraceStatus status2 = traceLog.begin("message2");
        traceLog.exception(status2, new IllegalArgumentException("예외 발생!"));
        traceLog.end(status1);

        assertThat(getContainsLog("message1")).isPresent();
        assertThat(getContainsLog("|-->message2")).isPresent();
        assertThat(getContainsLog("|<X-message2")).isPresent();
        assertThat(getContainsLog("message1")).isPresent();
    }
}
