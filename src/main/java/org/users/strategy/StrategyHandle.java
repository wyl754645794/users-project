package org.users.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * StrategyHandle
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:37 PM:12
 */
public class StrategyHandle {

    private final List<Strategy> strategieChain = new ArrayList<>();

    public StrategyHandle addStrategy(Strategy strategy){
        strategieChain.add(strategy);
        return this;
    }

    public Object doStrategy(Object condition,String command,Object... params){
        for (Strategy strategy : strategieChain) {
            if (strategy.judge(condition)){
                return strategy.execute(command,params);
            }
        }
        System.err.println("strategy is not find");
        return null;
    }

    public List<Strategy> getStrategieChain() {
        return strategieChain;
    }
}
