package com.example.demo.dto;

import com.example.demo.annotation.NotAllowEmpty;
import com.example.demo.exception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Aspect
@Component
public class CheckParamsAspect {
    private Logger logger = LogManager.getLogger(CheckParamsAspect.class);
    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void checkParams(){}

    //环绕通知，环绕增强，相当于MethodInterceptor
    @Around("checkParams()")
    public Object arround(ProceedingJoinPoint pjp){
        System.out.println("检查参数start...");
        try{
            //Parameter Values
            Object[] params = pjp.getArgs();
            for (Object param:params){
                System.out.println(param);
            }
            System.out.println("======================================");

            //Controller name: com.example.demo.controller.HelloController
            Class<?> classTarget = pjp.getTarget().getClass();
            System.out.println(classTarget.getName());

            //Method name: hello1
            String methodName = pjp.getSignature().getName();
            System.out.println(methodName);

            //Parameter Type: java.lang.String
            Class<?>[] paramTypes = ((MethodSignature)pjp.getSignature()).getParameterTypes();
            for (Class<?> paramType : paramTypes){
                System.out.println(paramType.getName());
            }

            //Parameter Name: label
            String [] parameterNames= ((MethodSignature)pjp.getSignature()).getParameterNames();
            for (String parameterName : parameterNames){
                System.out.println(parameterName);
            }

            Method objMehtod = classTarget.getMethod(methodName, paramTypes);
            //一个参数可能会有多个注解。objMehtod.getParameterAnnotations()[i] 表示第i个参数的注解数组
            Annotation[][] parameterAnnotations =  objMehtod.getParameterAnnotations();
            for (int i=0; i<parameterAnnotations.length; i++){
                Annotation[] annotations = parameterAnnotations[i];
                Object param = pjp.getArgs()[i];
                System.out.println("param: " + param);
                for (Annotation annotation : annotations){
                    System.out.println(annotation.toString());
                    if (annotation instanceof NotAllowEmpty){
                        System.out.println("not allow empty");
                    }
                }
            }

            Object o = pjp.proceed();
            return o;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
}
