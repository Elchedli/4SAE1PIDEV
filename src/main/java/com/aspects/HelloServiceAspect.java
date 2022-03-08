package com.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloServiceAspect {
	@Before("execution(* com.services.Implementations.AdServiceImp.StatsAd(..))")
	public void before(){
		System.out.println("Avant execution du statistique.");
	}
	
	@AfterReturning("execution(* com.services.Implementations.AdServiceImp.StatsAd(..))")
	public void afterReturning(){
		System.out.println("Retour execution de la fonction statistique");
	}
		
	@After("execution(* com.services.Implementations.AdServiceImp.StatsAd(..))")
	public void after(){
		System.out.println("Apres execution de la fonction statistique");
	}
	
//	@Around("execution(* com.implementation.AdServiceImp.StatsAd(..))")
//	public Object around(ProceedingJoinPoint joinPoint){
//		System.out.println("Une autre chose de test!!!!");
//		return "autre chose";
//	}
}
