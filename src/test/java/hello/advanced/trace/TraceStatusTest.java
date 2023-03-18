package hello.advanced.trace;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TraceStatusTest {
    @Test
    @DisplayName("생성자")
    void constructor() {
        //given
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        String message = "message";
        //when
        TraceStatus traceStatus = new TraceStatus(traceId, startTimeMs, message);
        //then
        assertThat(traceStatus.getTraceId()).isEqualTo(traceId);
        assertThat(traceStatus.getStartTimeMs()).isEqualTo(traceId);
        assertThat(traceStatus.getMessage()).isEqualTo(traceId);
    }
}
