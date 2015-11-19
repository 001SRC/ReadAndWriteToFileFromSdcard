/**
 * 
 **/
package org.virsms.readAndWriteContentsFromFileOnSdcard;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * @Plank
 * @org.virsms.readAndWriteContentsFromFileOnSdcard
 * @author virsms
 * @date 2015年11月18日
 * @tme 上午5:42:44
 * @todo
 *       LogCat 设置过滤器，logcat filter中设置 "filter name" 和 "by log tag" 成 TAG_Read(TAG_Write)的内容，"by log level"设置为 info, 内容都可以根据自己喜好设置。
 *       结果预览：
 *       TAG_Read_Write_Logcat_filter.png
 */
public class MyTest extends AndroidTestCase {

	private final String	TAG_Read	= "ReadContentsFromFileOnSdcard";
	private final String	TAG_Write	= "WriteContentsFromFileOnSdcard";

	public MyTest() {
	}

	/**
	 * 测试能否写入sdcard上文件
	 */
	public void savedFile() {
		Context context = getContext();
		FileService fleService = new FileService(context);
		boolean flag = fleService.writeContentsToFileOnSdcard("readme.txt", "被写入的内容。");
		Log.i(TAG_Write, " ★★★★★ 写入成功。" + flag + "★★★★★");
	}

	/**
	 * 测试能否读出sdcard文件的内容
	 */
	public void readFile() {
		FileService fileService = new FileService(getContext());
		String readFileMsg = fileService.readFileContentsFromFileOnSdcard("readme.txt");
		Log.i(TAG_Read, " ★★★★★ 读取成功。" + readFileMsg + "★★★★★");
	}
}
