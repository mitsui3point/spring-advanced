package hello.advanced.exampleonly.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식
 *
 * 전략 패턴
 *
 * 정리
 * - 지금까지 일반적으로 이야기하는 전략 패턴에 대해서 알아보았다.
 * - 변하지 않는 부분을 `Context`에 두고,
 *   변하는 부분을 `Strategy`를 구현해서 만든다.
 * - 그리고 `Context`의 내부 필드에 `Strategy`를 주입해서 사용했다.
 *
 * 선 조립, 후 실행
 * - 여기서 이야기하고 싶은 부분은 `Context` 내부 필드에 `Strategy` 를 두고 사용하는 부분이다.
 * - 이 방식은 `Context` 와 `Strategy` 를 실행 전에 원하는 모양으로 조립해두고,
 *   그 다음에 `Context` 를 실행하는
 *   선 조립, 후 실행 방식에서 매우 유용하다.
 * - `Context` 와 `Strategy`를 한번 조립하고 나면 이후로는 `Context` 를 실행하기만 하면 된다.
 *   우리가 스프링으로 애플리케이션을 개발 할 때
 *   애플리케이션 로딩 시점에 의존관계 주입을 통해
 *   필요한 의존관계를 모두 맺어 놓고 난 뒤에
 *   실제 요청을 처리하는 것과 같은 원리이다.
 * - 이 방식의 단점은 Context 와 Strategy 를 조립한 이후에 전략을 변경하기가 번거롭다는 점이다.
 *   물론 Context 에 setter 를 제공해서 Strategy 를 넘겨 받아 변경하면 되지만,
 *   Context 를 싱글톤으로 사용할 때는 동시성 이슈 등 고려할점이 많다.
 *   그래서 전략을 실시간으로 변경해야 하면 차라리 이전에 개발한 테스트 코드처럼
 *   Context 를 하나 더 생성하고 그 곳에 다른 Strategy 를 주입하는 것이 더 나은 선택일 수 있다.
 */
@Slf4j
public class ContextV1 {
    private Strategy strategy;
    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call();//위임
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

}
