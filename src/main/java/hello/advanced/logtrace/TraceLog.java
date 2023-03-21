package hello.advanced.logtrace;

import hello.advanced.trace.TraceStatus;

public interface TraceLog {
    TraceStatus begin(String message);

    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
