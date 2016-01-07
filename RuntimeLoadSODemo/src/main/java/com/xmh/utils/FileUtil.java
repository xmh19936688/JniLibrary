package com.xmh.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

	public static void copyFile(InputStream inStream, FileOutputStream outStream) throws IOException {
		int bytesum = 0;
		int byteread = 0;
		byte[] buffer = new byte[1444];
		int length;
		while ( (byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; //字节数 文件大小
			System.out.println(bytesum);
			outStream.write(buffer, 0, byteread);
		}
		outStream.flush();
	}

}
