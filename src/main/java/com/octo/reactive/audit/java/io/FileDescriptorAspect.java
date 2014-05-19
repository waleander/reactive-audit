package com.octo.reactive.audit.java.io;

import com.octo.reactive.audit.LatencyLevel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.io.OutputStream;

/**
 * Created by pprados on 19/05/2014.
 */
//TODO: Test
@Aspect
public class FileDescriptorAspect  extends AbstractOutputStreamAudit
{
	@Before("call(* java.io.FileDescriptor.*())")
	public void advice_high(JoinPoint thisJoinPoint)
	{
		latency(LatencyLevel.MEDIUM,thisJoinPoint,(OutputStream)thisJoinPoint.getTarget());
	}

}