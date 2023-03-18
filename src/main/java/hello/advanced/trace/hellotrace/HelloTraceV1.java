package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus start(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}",
                traceId.getId(),
                addSpace(START_PREFIX, traceId.getLevel()),
                message
                );
        return new TraceStatus(traceId,
                startTimeMs,
                message);
    }

    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long endTimeMs = System.currentTimeMillis();
        Long resultTimeMs = endTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        String message = status.getMessage();
        if (e != null) {
            log.info("[{}] {}{} time={}ms ex={}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    message,
                    resultTimeMs,
                    e.getClass()
            );
            return;
        }
        log.info("[{}] {}{} time={}ms",
                traceId.getId(),
                addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                message,
                resultTimeMs
        );
    }

    //레벨0:""
    //레벨1:"|-->"
    //레벨2:"|   |-->"
    //레벨3:"|   |   |-->"
    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < level; index++) {
            sb.append(appendPrefix(prefix, level, index));
        }
        return sb.toString();
    }

    private String appendPrefix(String prefix, int level, int index) {
        if (index == level - 1) {
            return "|" + prefix;
        }
        return "|   ";
    }
}
