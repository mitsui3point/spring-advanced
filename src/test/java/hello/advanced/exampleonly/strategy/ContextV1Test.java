package hello.advanced.exampleonly.strategy;

import hello.advanced.exampleonly.strategy.code.strategy.ContextV1;
import hello.advanced.exampleonly.strategy.code.strategy.Strategy;
import hello.advanced.exampleonly.strategy.code.strategy.StrategyLogic1;
import hello.advanced.exampleonly.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    @Test
    void strategyV0() {
        //when
        logic1();
        logic2();
    }

    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1() {
        Strategy strategyLogic1 = new StrategyLogic1();
        ContextV1 strategyV1 = new ContextV1(strategyLogic1);
        strategyV1.execute();

        Strategy strategyLogic2 = new StrategyLogic2();
        ContextV1 strategyV2 = new ContextV1(strategyLogic2);
        strategyV2.execute();
    }

    /**
     * 익명 인터페이스 구현
     */
    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1={}", strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        log.info("strategyLogic2={}", strategyLogic2);
        context2.execute();
    }

    /**
     * 익명 인터페이스 구현 -> 인라인
     */
    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * 익명 인터페이스 구현 -> 인라인 -> 람다
     * - 익명 내부 클래스를 자바 8 부터 람다로 변경할 수 있다.
     * - 람다로 변경하려면 인터페이스에 메서드가 1개만 있으면 되는데, 여기에서 제공하는 'strategy' 인터페이스는 메서드가 1개만 있으므로 람다를 사용할 수 있다.
     */
    @Test
    void strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        context2.execute();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
