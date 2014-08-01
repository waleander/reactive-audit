package com.octo.reactive.audit.java.io;

import com.octo.reactive.audit.AbstractFileAudit;
import com.octo.reactive.audit.AuditReactive;
import com.octo.reactive.audit.FactoryException;
import com.octo.reactive.audit.FileTools;
import com.octo.reactive.audit.lib.AuditReactiveException;
import com.octo.reactive.audit.lib.Latency;
import org.aspectj.lang.JoinPoint;

import java.io.Writer;

import static com.octo.reactive.audit.FileTools.*;

public abstract class AbstractWriterAudit extends AbstractFileAudit
{
	public static AuditReactiveException latencyWriter(AuditReactive config, Latency latency, JoinPoint thisJoinPoint, Writer writer)
			throws AuditReactiveException
	{
		AuditReactiveException ex = null;
		CharSequence msg = null;
		if (config.isDebug())
			msg = FileTools.dumpChain(writer);
		switch (isLastOutputStreamFromWriterWithLatency(writer))
		{
			case NET_ERROR:
				ex = FactoryException.newNetwork(latency, thisJoinPoint, msg);
				break;
			case FILE_ERROR:
				ex = FactoryException.newFile(latency, thisJoinPoint, msg);
				break;
		}
		return ex;
	}

	protected void latency(Latency latency, JoinPoint thisJoinPoint, Writer writer) throws AuditReactiveException
	{
		AuditReactiveException ex = latencyWriter(config, latency, thisJoinPoint, writer);
		if (ex != null) super.logLatency(latency, thisJoinPoint, ex);
	}
}
