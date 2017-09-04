package com.smart.pointcut;

import com.smart.domain.Leaderdelegate;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class GreetingComposablePointcut {

    public Pointcut getIntersectionPointcut() {
        ComposablePointcut cp = new ComposablePointcut();
        Pointcut pt1 = new ControlFlowPointcut(Leaderdelegate.class, "service");
        NameMatchMethodPointcut pt2 = new NameMatchMethodPointcut();
        pt2.addMethodName("greet");
        pt2.addMethodName("service");
        return cp.intersection(pt1).intersection((Pointcut) pt2);
    }
}
