package com.imooc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServiceLogAspect
 * @Descrintion
 * @Author bd
 * @Date 2020/5/23 22:46
 * @Version 1.0
 **/
//@Component
////开启aop
//@Aspect
public class ServiceLogAspect {

    public static final Logger logger =
            LoggerFactory.getLogger(ServiceLogAspect.class);
    /*
     *前置通知：在方法调用之前执行
     *后置通知：在方法正确调用之后执行（如果报异常，不会执行）
     *环绕通知：在方法调用之前和之后，都分别可以执行的通知
     *异常通知: 如果在方法调用过程中都发生异常，则通知
     *最终通知：在方法调用之后执行（都会执行）
     **/

    /*
     * @Author bd
     * @Description 切面表达式：
     *      execution  代表要执行的主体
     *    第一处* 代表方法返回类型 ，*代表所有返回类型
     *     第二处 包名  代表aop所监控的包
     *    第三处.. 代表该包及其子包下面  的所有类方法
     *     第四处 * *代表所有类
     *    第五处 *（..） * 类中的方法名，(..) 方法中的任何参数
     * 还有一个点 类.方法（形参）
     * @Date 23:05 2020/5/23
     * @Param [joinPoint]
     * @return java.lang.Object
     **/
    @Around("execution(* com.imooc.controller..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //{} 在log4j中相当于一个占位符 类名  方法名
        logger.info("===== 开始执行 {}.{} =====",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        //记录开始时间
        long begin = System.currentTimeMillis();
        //执行目标 service方法
        Object result = joinPoint.proceed();
        //记录结束时间
        long end = System.currentTimeMillis();

        long takeTime = end - begin;

        if (takeTime > 3000) {
            logger.error("====== 执行结束，耗时：{}毫秒 ======", takeTime);
        } else if (takeTime > 2000) {
            logger.warn("====== 执行结束，耗时：{}毫秒 ======", takeTime);
        } else {
            logger.info("====== 执行结束，耗时：{}毫秒 ======", takeTime);
        }
        return result;

    }


}
