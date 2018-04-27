package com.example.demo.dto;

import com.example.demo.controller.HelloController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
public class FirstAspect {
    private Logger logger = LogManager.getLogger(FirstAspect.class);
    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws  Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println("URL: " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD: " + request.getMethod());
        System.out.println("IP: " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning="ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws  Throwable{
        System.out.println("方法的返回值：" + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行...");
    }

    //后置最终通知，final增强，不管是抛出异常或正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        System.out.println("方法最后执行...");
    }

    //环绕通知，环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp){
        System.out.println("方法环绕start...");
        try{
            Object o = pjp.proceed();
            System.out.println("方法环绕proceed, 结果是：" + o);
            return o;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }



}
