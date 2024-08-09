package com.xlw.demo.util.BOSEBank;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class sha {

	public static void main(String[] args) {

		try {
			// 文件内容
			byte[] fileContent = readFile("c:/a.txt");
			// 计算文件摘要
			byte[] shaByte = MessageDigest.getInstance("SHA-256").digest(fileContent);
			// 文件摘要16进制表示(大写)
			String hexStr = byte2Hex(shaByte);
			// 签名字符串
			String singStr = SignOpStep.getNodeValue((new SignOpStep(hexStr)).sign(), "signed_data");
			// 上送银企的sha参数
			String sha = BOS_URLencode(singStr);
			System.out.println("上送银企系统的sha参数:" + sha);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	private static byte[] readFile(String file) {
		ByteOutputStream bout = new ByteOutputStream();
		try {
			File f = new File(file);
			int b = 0;
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
			while ((b = br.read()) != -1) {
				bout.write(b);
			}
			br.close();

		} catch (Exception e) {
		}
		return bout.toByteArray();
	}

	public static String byte2Hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		// 注意，此处要把16进制转化成大写
		return hs.toString().toUpperCase();
	}

	private static String BOS_URLencode(String orgStr) {
		String newStr = null;
		try {
			newStr = java.net.URLEncoder.encode(orgStr, "GBK");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return newStr;
	}
}
