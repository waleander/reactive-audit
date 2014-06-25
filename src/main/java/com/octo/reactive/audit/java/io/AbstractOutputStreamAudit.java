package com.octo.reactive.audit.java.io;

import com.octo.reactive.audit.FileAudit;
import com.octo.reactive.audit.lib.Latency;
import com.octo.reactive.audit.lib.NetworkAuditReactiveException;
import org.aspectj.lang.JoinPoint;

import java.io.OutputStream;

import static com.octo.reactive.audit.java.io.FileTools.*;

class AbstractOutputStreamAudit extends FileAudit
{
	protected void latency(Latency level, JoinPoint thisJoinPoint, OutputStream out)
	{
		switch (isLastOutputStreamWithLatency(out))
		{
			case NET_ERROR:
				super.latency(level, thisJoinPoint,
				              new NetworkAuditReactiveException(thisJoinPoint.getSignature().toString()));
				break;
			case FILE_ERROR:
				super.latency(level, thisJoinPoint);
				break;
			default:
				// Nothing
		}
	}
}
