package com.octo.reactive.audit.javax.imageio;

import com.octo.reactive.audit.AbstractFileAudit;
import com.octo.reactive.audit.URLTools;
import com.octo.reactive.audit.java.io.AbstractInputStreamAudit;
import com.octo.reactive.audit.java.io.AbstractOutputStreamAudit;
import com.octo.reactive.audit.lib.AuditReactiveException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static com.octo.reactive.audit.lib.Latency.HIGH;

// Nb methods: 5
@Aspect
public class ImageIOAudit extends AbstractFileAudit
{
	@Before("call(* javax.imageio.ImageIO.read(java.io.File))")
	public void read(JoinPoint thisJoinPoint)
	{
		latency(HIGH, thisJoinPoint);
	}

	@Before("call(* javax.imageio.ImageIO.read(java.io.InputStream)) && args(in)")
	public void read(JoinPoint thisJoinPoint, InputStream in)
	{
		AuditReactiveException ex = AbstractInputStreamAudit.latencyInputStream(config, HIGH, thisJoinPoint, in);
		if (ex != null) super.logLatency(HIGH, thisJoinPoint, ex);
	}

	@Before("call(* javax.imageio.ImageIO.read(java.net.URL)) && args(url)")
	public void read(JoinPoint thisJoinPoint, URL url)
	{
		AuditReactiveException ex = URLTools.latencyURL(config, thisJoinPoint, url);
		if (ex != null) super.logLatency(HIGH, thisJoinPoint, ex);
	}

	@Before("call(* javax.imageio.ImageIO.write(java.awt.image.RenderedImage, String, java.io.File))")
	public void write(JoinPoint thisJoinPoint)
	{
		latency(HIGH, thisJoinPoint);
	}

	@Before("call(* javax.imageio.ImageIO.write(java.awt.image.RenderedImage, String, java.io.OutputStream)) && args(r,s,out)")
	public void write(JoinPoint thisJoinPoint, RenderedImage r, String s, OutputStream out)
	{
		latency(HIGH, thisJoinPoint);
		AuditReactiveException ex = AbstractOutputStreamAudit.latencyOutputStream(config, HIGH, thisJoinPoint, out);
		if (ex != null) super.logLatency(HIGH, thisJoinPoint, ex);
	}

}
