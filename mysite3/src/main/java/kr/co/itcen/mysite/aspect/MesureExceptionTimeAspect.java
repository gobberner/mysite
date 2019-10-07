package kr.co.itcen.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MesureExceptionTimeAspect {
	@Around("execution(* *..controller.*.*(..))||execution(* *..service.*.*(..))||execution(* *..repository.*.*(..))")
	public Object aroundAspect(ProceedingJoinPoint pjp) throws Throwable{
		
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		Object result =pjp.proceed();
				
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		//어떤 클래스의 어떤 메서드
		String className =pjp.getTarget().getClass().getName();
		String methodName =pjp.getSignature().getName();
		String taskName = className + "." +methodName;
		
		System.out.println("[Execution Time]["+taskName+"]:"+totalTime+"millis");
		
		
		return result;
	}
}
