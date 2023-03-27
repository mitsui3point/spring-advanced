package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 메서드 패턴 적용
 * - 템플릿 메서드 패턴 덕분에 변하는 코드와 변하지 않는 코드를 명확하게 분리했다.
 * - 로그를 출력하는 템플릿 역할을 하는 변하지 않는 코드는 모두 이곳에 담아두고, 변하는 코드는 자식 클래스로 만들어서 분리했다.
 *
 * 좋은 설계란?
 * - 진정한 좋은 설계는 바로 '변경'이 일어날 때 자연스럽게 드러난다.
 * - 지금까지 로그를 남기는 부분을 모아서 하나로 모듈화하고, 비즈니스 로직 부분을 분리했다.
 * - 만약에 V4 app 에서 로그를 남기는 로직을 변경해야 하는 상황이 생긴다고 하자. 이 때는 단순히 {@link AbstractTemplate} 이 코드만 고치면 된다.
 * - 템플릿이 없는 V3 app 의 상태에서는 로그를 남기는 로직을 변경해야 한다고 생각해보자. 이 때는 V3 app 의 모든 코드들을 수정해야 하는 상황이 생긴다.
 *
 * 단일 책임 원칙(SRP; Single Responsibility Principle)
 * - V4 는 단순히 템플릿 메서드 패턴을 적용해서 소스코드 몇 줄을 줄인 것이 전부가 아니다.
 * - 로그를 남기는 부분에 단일 책임 원칙(SRP)을 지킨 것이다.
 * - 변경 지점을 하나로 모아서 변경에 쉽게 대처할 수 있는 구조를 만든 것이다.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {
    private final LogTrace trace;

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    public abstract T call();
}
