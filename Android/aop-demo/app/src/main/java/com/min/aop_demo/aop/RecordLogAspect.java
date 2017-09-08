package com.min.aop_demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import timber.log.Timber;

/**
 * Created by minyangcheng on 2017/9/7.
 */

@Aspect
public class RecordLogAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.min.aop_demo.aop.RecordLog * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.min.aop_demo.aop.RecordLog *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        RecordLog recordLog = methodSignature.getMethod().getAnnotation(RecordLog.class);
        Timber.d("before className=" + className + " methodName=" + methodName + " value=" + recordLog.value());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Timber.d("after time consume=%s", (System.currentTimeMillis() - startTime));
        return result;
    }

}
