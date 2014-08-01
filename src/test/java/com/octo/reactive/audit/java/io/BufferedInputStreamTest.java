package com.octo.reactive.audit.java.io;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.octo.reactive.audit.TestTools.pop;
import static com.octo.reactive.audit.TestTools.push;

public class BufferedInputStreamTest extends FileInputStreamTest
{
	@Override
	protected InputStream newInputStream() throws IOException
	{
		push();
		InputStream out = super.newInputStream();
		pop();
		return new BufferedInputStream(out);
	}

	@Override
	@Test
	public void New() throws IOException
	{
		super.New();
	}

	@Test
	public void derived()
	{
		class Derived extends BufferedInputStream
		{
			public Derived()
			{
				super(new ByteArrayInputStream(new byte[10]));
			}
		}
		new Derived();
	}
}
