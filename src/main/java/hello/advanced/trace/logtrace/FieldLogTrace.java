package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder;//traceId 동기화, 동시성 이슈 발생

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();

        TraceId traceId = traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();

        printBeginLog(message, traceId);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    //private logic

    private void releaseTraceId() {
        if (!traceIdHolder.isFirstLevel()) {
            traceIdHolder = traceIdHolder.createPreviousId();
            return;
        }
        traceIdHolder = null;//destroy
    }
    private void syncTraceId() {
        if (traceIdHolder != null) {
            traceIdHolder = traceIdHolder.createNextId();
            return;
        }
        traceIdHolder = new TraceId();
    }

    private void complete(TraceStatus status, Exception e) {
        Long endTimeMs = System.currentTimeMillis();
        Long resultTimeMs = endTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        String message = status.getMessage();

        if (e != null) {
            printExceptionLog(e, resultTimeMs, traceId, message);
            return;
        }

        printEndLog(resultTimeMs, traceId, message);
    }

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

    private void printBeginLog(String message, TraceId traceId) {
        log.info("[{}] {}{}",
                traceId.getId(),
                addSpace(START_PREFIX, traceId.getLevel()),
                message
        );
    }

    private void printEndLog(Long resultTimeMs, TraceId traceId, String message) {
        log.info("[{}] {}{} time={}ms",
                traceId.getId(),
                addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                message,
                resultTimeMs
        );
        releaseTraceId();
    }

    private void printExceptionLog(Exception e, Long resultTimeMs, TraceId traceId, String message) {
        log.info("[{}] {}{} time={}ms ex={}",
                traceId.getId(),
                addSpace(EX_PREFIX, traceId.getLevel()),
                message,
                resultTimeMs,
                e.getClass()
        );
        releaseTraceId();
    }
}
