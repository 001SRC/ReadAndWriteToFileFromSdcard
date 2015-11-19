package org.virsms.readAndWriteContentsFromFileOnSdcard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileService {
	Context					context				= null;
	private final String	SDCARD_IsFineOrNot	= Environment.MEDIA_MOUNTED;

	public FileService() {
	}

	public FileService(Context context) {
		this.context = context;
	}

	/**
	 * @develop environments
	 *          win7_sp1、
	 *          Eclipse Java EE IDE Version: Luna Service Release 1 (4.4.1) Build id: 20140925-1800、
	 *          android:minSdkVersion="8" android:targetSdkVersion="21"
	 * @to do
	 *     总共两个目标：1. 将内容(字符串)写入 sd卡 txt文件上。2.从 sd卡 txt文件上读取文件(字符串)的内容。
	 *     实现功能上比较粗糙，还有多处优化空间。
	 *     后期拓展的目标：1.实现 sd卡任意文件内容的写入。2. 从 sd卡读取任意内容。3.上传、下载。（同名文件的处理）
	 * @how
	 *      1. 写文件前
	 *      关键的类（接口）：FileOutputStream、FileIutputStream、File、
	 *      关键点：读写时文件内容形式如何转换的，读写前先进行 sd卡状态验证，读文件内容时 ByteArrayOutputStream
	 * @param filename
	 *            ,写入sdcard的文件名
	 * @param fileContext
	 *            ,写入sdcard的文件内容
	 * @others
	 *         ,读写测试在 MyTest.java
	 *         代码参考：《老罗Android开发视频教程》中 “android存储数据和文件” 中 “第一集：android读写sdcard”
	 */
	@SuppressWarnings("resource")
	public boolean writeContentsToFileOnSdcard(String filename, String content) {
		/*
		 * boolean flag = false; 写入文件成功与否
		 * F2此处查看官方解释：java.io.FileOutputStream
		 * An output stream that writes bytes to a file. If the output file exists, it can be replaced or appended to. If it does not exist, a new file will be created.
		 * Environment.MEDIA_MOUNTED 判断sdcard挂在状态,是否可用
		 */
		FileOutputStream fileOutputStream = null;
		boolean flag = false;
		File file = new File(Environment.getExternalStorageDirectory(), filename);
		if (Environment.getExternalStorageState().equals(SDCARD_IsFineOrNot)) {
			try {
				fileOutputStream = new FileOutputStream(file);
			}
			catch (FileNotFoundException e2) {
				Log.i("new FileOutputStream(file)", "sd卡上未发现该文件。");
			}
			try {
				fileOutputStream.write(content.getBytes());
				return flag = true;
			}
			catch (IOException e1) {
				Log.i("content.getByte():", "写入内容时出错。");
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				}
				catch (IOException e) {
					Log.i("fileOutputStream.close():", "输出流没有关闭。");
				}
			}
		}
		return flag;
	}

	@SuppressWarnings({ "resource", "unused" })
	public String readFileContentsFromFileOnSdcard(String fileName) {
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		File file = new File(Environment.getExternalStorageDirectory(), fileName);
		if (SDCARD_IsFineOrNot.equals(Environment.getExternalStorageState())) {
			try {
				fileInputStream = new FileInputStream(file);
				int len = 0;
				byte[] data = new byte[1234];
				try {
					if ((len = fileInputStream.read(data)) != -1) {
						outputStream.write(data, 0, len);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			finally {
				try {
					fileInputStream.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return (new String(outputStream.toByteArray()));
	}
}
