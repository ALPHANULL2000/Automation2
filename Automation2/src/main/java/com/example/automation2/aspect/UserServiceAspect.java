package com.example.automation2.aspect;

import com.example.automation2.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/*** Created: 8/16/2021 --- 4:20 PM --- ALPHANULL ***/
// All aspects controllers for UserService class (AOP-Pattern)

@Aspect @Component
public class UserServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(UserServiceAspect.class);
    private final String Sep2 = "------------------------------------------------";

    private void loggerByAdvice(@NonNull String advice,String sign, String cls, boolean sep2) {
        System.out.println(Sep2+"\n"+advice);
        logger.info(advice+" --- "+sign+" --- "+cls+"\n");
        if(sep2)
            System.out.println(Sep2);
    }

    @Pointcut("execution(* com.example.automation2.service.UserService.*(..))")
    public void pointcut() { }

    @Before("pointcut()")
    public void beforeAdvice(@NonNull JoinPoint point) {
        loggerByAdvice("BEFORE",point.getSignature().getName(),
                point.getTarget().getClass().getSimpleName(),true);
    }

    @Around("pointcut()")
    public Object aroundAdvice(@NonNull ProceedingJoinPoint proceed) {
        Long sTime = System.currentTimeMillis();
        Object object = null;
        try {
            object = proceed.proceed();
        } catch(Throwable throwable) {
            System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
        Long eTime = System.currentTimeMillis();
        String tTime = String.valueOf(eTime-sTime);
        loggerByAdvice("AROUND("+tTime+" ms)",proceed.getSignature().getName(),
                proceed.getTarget().getClass().getSimpleName(),false);
        System.out.println(Sep2);
        return object;
    }

    @AfterReturning(pointcut = "pointcut()", returning = "user")
    public void afterReturnAdvice(@NonNull JoinPoint point, @NonNull List<User> user) {
        loggerByAdvice("AFTER_RETURNING",point.getSignature().getName(),
                point.getTarget().getClass().getSimpleName(),false);
        for(User userItem: user) System.out.println(userItem);
        System.out.println(Sep2);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "exception")
    public void afterThrow(@NonNull JoinPoint point, @NonNull Exception exception) {
        loggerByAdvice("AFTER_THROWING",point.getSignature().getName(),
                point.getTarget().getClass().getSimpleName(),false);
        System.out.println(exception.getMessage());
        System.out.println(Sep2);
    }

    @After("pointcut()")
    public void afterAdvice(@NonNull JoinPoint point) {
        loggerByAdvice("AFTER",point.getSignature().getName(),
                point.getTarget().getClass().getSimpleName(),true);
    }
}


