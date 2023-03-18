package hello.advanced.trace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TraceIdTest {
    @Test
    @DisplayName("생성자")
    void constructor() {
        //given
        //when
        TraceId traceId = new TraceId();
        String id = traceId.getId();
        int level = traceId.getLevel();
        //then
        assertThat(id).hasSize(8);
        assertThat(level).isEqualTo(0);
    }

    @Test
    @DisplayName("다음 id 찾기")
    void createNextId() {
        //given
        TraceId traceId = new TraceId();
        //when
        TraceId nextId = traceId.createNextId();
        int level = nextId.getLevel();
        //then
        assertThat(level).isEqualTo(1);
    }

    @Test
    @DisplayName("이전 id 찾기")
    void createPreviousId() {
        //given
        TraceId nextId = new TraceId().createNextId();
        //when
        TraceId previousId = nextId.createPreviousId();
        int nextIdLevel = nextId.getLevel();
        int previousIdLevel = previousId.getLevel();
        //then
        assertThat(nextIdLevel).isEqualTo(1);
        assertThat(previousIdLevel).isEqualTo(0);
    }

    @Test
    @DisplayName("첫번째 id 여부 확인")
    void isFirstLevel() {
        //given
        TraceId traceId = new TraceId();
        TraceId nextId = traceId.createNextId();
        TraceId previousId = nextId.createPreviousId();
        //when
        boolean isTraceIdFirstLevel = traceId.isFirstLevel();
        boolean isNextIdFirstLevel = nextId.isFirstLevel();
        boolean isPreviousIdFirstLevel = previousId.isFirstLevel();
        //then
        assertThat(isTraceIdFirstLevel).isTrue();
        assertThat(isNextIdFirstLevel).isFalse();
        assertThat(isPreviousIdFirstLevel).isTrue();
    }
}
