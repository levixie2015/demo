package com.xlw.demo.util.BOSEBank;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * 本示例包含
 * 1:查询类，
 * 2:转账类，
 * 3:文件上传
 * 4：文件下载
 * 几种代码案例，仅供参考
 * </pre>
 * */
public class TestBankExpress1_1 {
	protected String sendData = "";

	protected String http = "http";

	protected String ip = "127.0.0.1";

	protected String userID = "9892327";
	protected String userPWD = "245771";

	protected String port = "7071";

	protected String split = "&";

	protected String account = "";

	protected String sessionid = "";

	protected String serialNo = "";

	protected java.net.HttpURLConnection urlConnection = null;

	protected String sendDataForBatchQueryBalanceCurrentOp = "";//账户余额查询
	protected String transferInner1_1Op = "transferInner1_1Op";// 行内公转公转账

	protected String CebankUserLogonOpForSign = ""; // ��¼

	/** 登陆交易 */
	public String logon() {
		this.sendData = this.CebankUserLogonOpForSign;
		System.out.println(CebankUserLogonOpForSign);
		String responsorUrl = this.http + "://" + this.ip + ":" + this.port + "/CM/APISessionReqServlet?";// 登录
		System.out.println(responsorUrl);
		try {
			java.net.URL aURL = new java.net.URL(responsorUrl);

			urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("User-Agent", "compatible; MSIE 5.0;");
			// urlConnection.setRequestProperty("User-Agent", "HTTP");
			urlConnection.connect();
			if (sendData != null && sendData.trim().length() != 0) {
				urlConnection.getOutputStream().write(sendData.getBytes());
			}

			int resCode = urlConnection.getResponseCode();
			int contentLen = urlConnection.getContentLength();

			java.io.DataInputStream in = new java.io.DataInputStream(urlConnection.getInputStream());

			byte buffer[] = new byte[contentLen];

			int len = 0;

			while (len < contentLen) {
				int remainedLen = contentLen - len;
				if (remainedLen > 1024)
					remainedLen = 1024;
				int readLen = in.read(buffer, len, remainedLen);
				if (readLen == -1 || readLen == 0) {
					break;
				}

				len = len + readLen;
			}

			urlConnection.disconnect();
			urlConnection = null;
			String a = new String(buffer, 0, contentLen);
			System.out.println(a);
			return a;

		} catch (Throwable e) {
			e.printStackTrace();
			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
					urlConnection = null;
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		} finally {
			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
					urlConnection = null;
				} catch (Exception ee) {
				}
			}
		}
		return "";
	}

	/** 一般交易 */
	public String hiscomm(String sendDatatemp) {
		this.sendData = sendDatatemp;
		String responsorUrl = this.http + "://" + this.ip + ":" + this.port + "/CM/APIReqServlet?";

		try {
			System.out.println(sendDatatemp);
			java.net.URL aURL = new java.net.URL(responsorUrl);

			urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			// urlConnection.setRequestProperty("User-Agent", "MSIE");
			urlConnection.setRequestProperty("User-Agent", "compatible; MSIE 5.0;");
			urlConnection.connect();
			if (sendData != null && sendData.trim().length() != 0) {
				OutputStream output = urlConnection.getOutputStream();
				output.write(sendData.getBytes("GBK"));
				output.flush();
				output.close();
			}

			int resCode = urlConnection.getResponseCode();
			int contentLen = urlConnection.getContentLength();

			java.io.DataInputStream in = new java.io.DataInputStream(urlConnection.getInputStream());

			byte buffer[] = new byte[contentLen];

			int len = 0;
			long start = System.currentTimeMillis();
			while (len < contentLen) {
				int remainedLen = contentLen - len;
				if (remainedLen > 1024)
					remainedLen = 1024;

				int readLen = in.read(buffer, len, remainedLen);

				if (readLen == -1 || readLen == 0) {
					break;
				}

				len = len + readLen;
			}
			long end = System.currentTimeMillis();
			System.out.println("处理完成，耗时：" + (end - start) + "毫秒");
			System.out.println("total nums=" + contentLen);
			urlConnection.disconnect();
			urlConnection = null;
			String a = new String(buffer, 0, contentLen, "GBK");
			return a;

		} catch (Throwable e) {
			e.printStackTrace();
			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
					urlConnection = null;
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		} finally {
			if (urlConnection != null) {
				try {
					urlConnection.disconnect();
					urlConnection = null;
				} catch (Exception ee) {
				}
			}
		}
		return "";
	}

	public void doTest() {
		String longonresult = null;
		String result = null;

		// 登录
		serialNo = String.valueOf(Math.round(Math.random() * 10000)) + System.currentTimeMillis();
		CebankUserLogonOpForSign = "opName=CebankUserLogon1_1Op"
				+ split
				+ "reqData="
				+ BOS_URLencode(SignOpStep.getNodeValue((new SignOpStep("<?xml version=\"1.0\" encoding=\"GBK\"?>"
						+ "<BOSEBankData>" + "<opReq>" + "<serialNo>" + serialNo + "</serialNo>" + "<reqTime>"
						+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</reqTime>" + "<ReqParam>" + "<userID>"
						+ userID + "</userID>" + "<userPWD>" + userPWD + "</userPWD>" + "</ReqParam>" + "</opReq>"
						+ "</BOSEBankData>")).sign(), "signed_data"));
		System.out.println(CebankUserLogonOpForSign);
		longonresult = logon();
		System.out.println("帐号登陆结果：=" + longonresult + "=");
		if (longonresult.substring(0, 1).equals("<")) {// 没有
			System.out.println("没有登陆正确:" + longonresult);
		} else {
			// 截取sessionid
			sessionid = longonresult.substring(0, 40);
			// 生成交易序列号
			serialNo = String.valueOf(Math.round(Math.random() * 10000)) + System.currentTimeMillis();
			try {
				// 查询普通帐户余额（查询一个/几个账户的余额）
				serialNo = String.valueOf(Math.round(Math.random() * 10000)) + System.currentTimeMillis();
				sendDataForBatchQueryBalanceCurrentOp = "dse_sessionId=" + sessionid + split
						+ "opName=batchQueryBalanceCurrent1_1Op" + split + "reqData="
						+ "<?xml version=\"1.0\" encoding=\"GBK\"?>" //
						+ "<BOSEBankData>" + "<opReq>" + "<serialNo>" + serialNo + "</serialNo>" //
						+ "<reqTime>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "</reqTime>" //
						+ "<ReqParam>" //
						+ "<SUMU>1</SUMU>"//
						+ "<opReqSet><opRequest>" //
						+ "<ACNO>31600703002050179</ACNO>" //
						+ "</opRequest></opReqSet></ReqParam></opReq>"//
						+ "</BOSEBankData>";
				System.out.println(sendDataForBatchQueryBalanceCurrentOp);
				result = hiscomm(sendDataForBatchQueryBalanceCurrentOp);
				System.out.println("账号余额查询返回：" + result);

				// 行内转账
				// serialNo = String.valueOf(Math.round(Math.random() * 10000))
				// + System.currentTimeMillis();
				// transferInner1_1Op = "dse_sessionId="
				// + sessionid
				// + split
				// + "opName=transferInner1_1Op"
				// + split
				// + "reqData="
				// + BOS_URLencode(SignOpStep.getNodeValue((new
				// SignOpStep("<?xml version=\"1.0\" encoding=\"GBK\"?>"
				// + "<BOSEBankData>" + "<opReq>" //
				// + "<serialNo>"
				// + serialNo
				// + "</serialNo>"
				// + "<reqTime>"
				// + "20080902"
				// + "</reqTime>"
				// + "<ReqParam>"
				// + "<ACNO>"
				// + account
				// + "</ACNO>"
				// + "<OPAC>"
				// + "31673500009000428"
				// + "</OPAC>"
				// + "<TRAM>"
				// + "1.08"
				// + "</TRAM>"
				// + "<NAME>"
				// + "行内转账测试帐号名称"
				// + "</NAME>"
				// + "<USAG>"
				// + ""
				// + "</USAG>"
				// + "<REMK>"
				// + ""
				// + "</REMK>"
				// + "</ReqParam>"
				// + "</opReq>"
				// + "</BOSEBankData>")).sign(), "signed_data"));
				// result = hiscomm(transferInner1_1Op);
				// System.out.println("[行内转账]返回：" + result);

				// // 文件上传
				// String fileName = "c:/a.txt";
				// System.out.println("上传文件:" + fileName);
				// String ret = FileTest.uploadFile(fileName);
				// System.out.println("文件上传结果:" + ret);

				// 文件下载
				// System.out.println("开始下载文件");
				// String ret = FileTest.downloadFile("", "", "");
				// System.out.println("文件下载结果:" + ret);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String BOS_URLencode(String orgStr) {
		String newStr = null;
		try {
			newStr = java.net.URLEncoder.encode(orgStr, "GBK");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return newStr;
	}

	public static void main(String[] arg) {
		new TestBankExpress1_1().doTest();
	}
}
