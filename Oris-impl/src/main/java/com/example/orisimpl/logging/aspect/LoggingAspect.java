package com.example.orisimpl.logging.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {


    @Pointcut("within(com.example.orisimpl..*)&& !bean(*Controller) && !execution(* com.example.orisimpl..security..*(..)) ")
    public void toLogAllMethods(){
    }


    @Pointcut("bean(*Controller) && !execution(* com.example.orisimpl..security..*(..))")
    public void toLogController(){
    }
}
