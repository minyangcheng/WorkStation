package com.min.aop_demo.record;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * Created by minyangcheng on 2017/9/7.
 */

@Aspect
public class RecordLogAspect {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String POINTCUT_METHOD =
            "execution(@com.min.aop_demo.record.RecordPoint * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithRecordLog() {
    }

    @Around("methodAnnotatedWithRecordLog()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        method.setAccessible(true);
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        String name = recordLog.value();
        Object result = joinPoint.proceed();
        String pointPos = className + "." + methodName + "()";
        RecordHandler.getInstance().handle(pointPos, name, result);
        return result;
    }

}
