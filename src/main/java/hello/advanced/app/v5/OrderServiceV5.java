package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceV5 {
    private final OrderRepositoryV5 repository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 repository, LogTrace trace) {
        this.repository = repository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", () -> {
            repository.save(itemId);
            return null;
        });
    }
}
