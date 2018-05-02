package com.example.demo.dto;

import com.example.demo.annotation.NotEmpty;
import com.example.demo.annotation.NotNull;
import com.example.demo.exception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class CheckParamsAspect {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void checkParamsPoint(){}

    //切面检查controller方法的参数
    @Around("checkParamsPoint()")
    public Object arround(ProceedingJoinPoint point)throws MyException{
        try{
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURL().toString();

            //Controller class
            Class<?> targetClass = point.getTarget().getClass();
            //Method name
            String methodName = point.getSignature().getName();
            //Parameter Types
            Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getParameterTypes();
            String[] parameterNames = ((MethodSignature)point.getSignature()).getParameterNames();
            //Controller function method
            Method method = targetClass.getMethod(methodName, parameterTypes);
            //一个参数可能会有多个注解。objMehtod.getParameterAnnotations()[i] 表示第i个参数的注解数组
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i=0; i<parameterAnnotations.length ; i++){
                Annotation[] annotations = parameterAnnotations[i];
                Object arg = point.getArgs()[i];
                String parameterName = parameterNames[i];
                for (Annotation annotation:annotations) {
                   ErrorInfo errorInfo = checkAnnotation(annotation, parameterName, arg, url);
                   if (errorInfo != null){
                       return errorInfo;
                   }
                }
            }
            Object o= point.proceed();
            return o;
        }catch (MyException myException){
            throw myException;
        }catch (Throwable e){
            logger.warn(e);
            return null;
        }
    }

    //根据参数的注解检查参数的有效性
    public ErrorInfo checkAnnotation(Annotation annotation, String argName, Object arg, String url)throws MyException{
        if(annotation instanceof NotEmpty){
            if(arg == null){
                return new ErrorInfo(String.format("The parameter %s can't be null.", argName), url);
            }
            if (arg instanceof String){
                if(((String) arg).isEmpty()){
                    return new ErrorInfo(String.format("The parameter %s can't be empty.", argName), url);
                }
            }else{
                throw new MyException(String.format("The parameter %s with annotation NotEmpty is not a String", argName));
            }
        }
        if(annotation instanceof NotNull && arg == null){
            return new ErrorInfo(String.format("The parameter %s can't be null.", argName), url);
        }
        return null;
    }
}
