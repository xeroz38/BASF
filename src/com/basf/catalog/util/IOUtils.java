package com.basf.catalog.util;

import android.database.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author $Author$
 * @version $Revision$
 */
public class IOUtils
{
	public static String read(InputStream in) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] bytes = new byte[1024];
		int read;

		while (-1 != (read = in.read(bytes)))
		{
			baos.write(bytes, 0, read);
		}

		return baos.toString();
	}
	
	public static void close(Cursor query)
	{
		try
		{
			if (query != null)
			{
				query.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void flush(Flushable flushable)
	{
		try
		{
			if (flushable != null)
			{
				flushable.flush();
			}
		}
		catch (IOException e)
		{
			// e.printStackTrace();
		}
	}

	public static void close(Closeable closeable)
	{
		try
		{
			if (closeable != null)
			{
				closeable.close();
			}
		}
		catch (IOException e)
		{
			// e.printStackTrace();
		}
	}
}
