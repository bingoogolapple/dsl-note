package cn.bingoogolapple.dslapp;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectDemo {

    @Pointcut("execution(* cn.bingoogolapple.dslapp.JavaAspectDemo.*(..))")
    public void javaMethodExecutionPointcut() {
    }

    @Pointcut("call(* cn.bingoogolapple.dslapp.JavaAspectDemo.*(..))")
    public void javaMethodCallPointcut() {
    }

    @Around("javaMethodExecutionPointcut()")
    public Object javaAroundWeaverPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        String result = String.format("Java Around 修改 %s 后的结果", name);
        Log.e("JavaAspectDemo", String.format("javaAroundWeaverPoint 修改 %s 结果：%s", name, joinPoint.toString()));
        return result;
    }

    @Before("javaMethodCallPointcut()")
    public void javaBeforeCall(JoinPoint joinPoint) {
        Log.e("JavaAspectDemo", "javaBeforeCall：" + joinPoint.toString());
    }

    @Pointcut("execution(* cn.bingoogolapple.dslapp.KotlinAspectDemo.*(..))")
    public void kotlinMethodExecutionPointcut() {
    }

    @Pointcut("call(* cn.bingoogolapple.dslapp.KotlinAspectDemo.*(..))")
    public void kotlinMethodCallPointcut() {
    }

    @Around("kotlinMethodExecutionPointcut()")
    public Object kotlinAroundWeaverPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        String result = String.format("Kotlin Around 修改 %s 后的结果", name);
        Log.e("KotlinAspectDemo", String.format("kotlinAroundWeaverPoint 修改 %s 结果：%s", name, joinPoint.toString()));
        return result;
    }

    @Before("kotlinMethodCallPointcut()")
    public void kotlinBeforeCall(JoinPoint joinPoint) {
        Log.e("KotlinAspectDemo", "kotlinBeforeCall：" + joinPoint.toString());
    }

    @Pointcut("execution(@cn.bingoogolapple.dslapp.SingleClick * *(..))")
    public void singleClickPointcut() {
    }

    @Around("singleClickPointcut()")
    public void singleClickAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            joinPoint.proceed();
        }
    }

    private static final long MIN_CLICK_DELAY_TIME = 600;
    private long lastClickTime = 0L;
}