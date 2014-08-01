package com.octo.reactive.audit.java.util.concurrent;

import com.octo.reactive.audit.AbstractCPUAudit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static com.octo.reactive.audit.lib.Latency.HIGH;

// Nb methods: 2
@Aspect
public class CyclicBarrierAudit extends AbstractCPUAudit
{
	@Before("call(* java.util.concurrent.CyclicBarrier.await(..) )")
	public void await(JoinPoint thisJoinPoint)
	{
		latency(HIGH, thisJoinPoint);
	}

}
