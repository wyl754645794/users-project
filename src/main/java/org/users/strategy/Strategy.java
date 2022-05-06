package org.users.strategy;

import java.lang.reflect.Method;

/**
 * Strategy
 *
 * @author YL.
 * @version V1.0
 * @date 2022-05-04 9:31 PM:00
 */
public interface Strategy {

    boolean judge(Object condition);

    default Object execute(String command,Object... params){
        Class<? extends Strategy> strategyClass = this.getClass();
        Method[] declaredMethods = strategyClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);
            if (command.equals(declaredMethod.getName())){
                try {
                    if (declaredMethod.getParameterCount() > 0){
                        return declaredMethod.invoke(this,params);
                    }
                    return declaredMethod.invoke(this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return "command is not find!";
    }

}
