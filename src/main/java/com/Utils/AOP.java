package com.Utils;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.services.Implementations.CommentImpl;



@Component
@Aspect

public class AOP {
	Logger logger=LoggerFactory.getLogger(AOP.class);
	@Autowired
	CommentImpl ci;
	
	
	

	public void writeInLikes(int id,JoinPoint joinpoint) throws IOException{
		LocalDateTime today =  LocalDateTime.now(); 
	//String name = joinPoint.getSignature().getName();

	//logger.info("like :"+date);
	//write here fl file
		try
		{
			
			FileWriter fileWriter = new FileWriter("likes.txt", true); //Set true for append mode
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(joinpoint.getSignature().getName()+id+" "+today);  //New line
			printWriter.close();
		/*PrintWriter writer = new PrintWriter("likes.txt", "UTF-8");
		writer.println("like"+"1 "+today+"\n");
		writer.close();	*/
		}
		
		catch(IOException e){
			System.out.println("FILE WRITING PROBLEM");
		}
	}
	
	
	@AfterReturning("execution(* com.services.Implementations.CommentImpl.*(..))")
	public void ups(JoinPoint joinPoint) throws IOException{
		if(CommentImpl.idstatic!=0)
		writeInLikes(CommentImpl.idstatic,joinPoint);
	}
		
		
/*
	@AfterReturning("execution(* org.hdev.compteservice.service.CommentImpl.*(..))")
	public void setStaticIdComment(JoinPoint joinPoint) throws IOException{
		
	
	}
		
	*/
	
	
	
	
}
	



