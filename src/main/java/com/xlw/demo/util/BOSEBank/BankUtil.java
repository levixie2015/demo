package com.xlw.demo.util.BOSEBank;

import java.io.UnsupportedEncodingException;


public class BankUtil {
    public static String sessionId = "";
    protected static String sendData = "";
    protected static String CebankUserLogonOpForSign = "";  //登录
    protected static java.net.HttpURLConnection urlConnection = null;
    protected static String signoffOp = "";//签退
    protected static final String split = "&";
    protected static String ipAddress = null;
    protected static String ports = null;

    public static String logon(String ip, String port) {
        ipAddress = ip;
        ports = port;
        sendData = CebankUserLogonOpForSign;
        String responsorUrl = "http" + "://" + ip + ":" + port
                + "/CM/APISessionReqServlet?";// 登陆
        try {
            java.net.URL aURL = new java.net.URL(responsorUrl);

            urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("User-Agent", "MSIE");
            //urlConnection.setRequestProperty("User-Agent", "HTTP");
            urlConnection.connect();
            if (sendData != null && sendData.trim().length() != 0) {
                urlConnection.getOutputStream().write(sendData.getBytes());
            }

            int resCode = urlConnection.getResponseCode();
            int contentLen = urlConnection.getContentLength();

            java.io.DataInputStream in = new java.io.DataInputStream(
                    urlConnection.getInputStream());

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

    public String off(String ip, String port) {
        this.sendData = this.signoffOp;
        String responsorUrl = "http" + "://" + ip + ":" + port
                + "/CM/APISignOffReqServlet?";// 登陆
        try {
            java.net.URL aURL = new java.net.URL(responsorUrl);

            urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("User-Agent", "MSIE");
            //urlConnection.setRequestProperty("User-Agent", "HTTP");
            urlConnection.connect();
            if (sendData != null && sendData.trim().length() != 0) {
                urlConnection.getOutputStream().write(sendData.getBytes());
            }

            int resCode = urlConnection.getResponseCode();
            int contentLen = urlConnection.getContentLength();

            java.io.DataInputStream in = new java.io.DataInputStream(
                    urlConnection.getInputStream());

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

    //登录
    public static String getSessionId(String ip, String port) {
        String longonresult = null;
        String result = null;
        BankUtil.ipAddress = ip;
        String serialNo = String.valueOf(Math.round(Math.random() * 10000))
                + System.currentTimeMillis();
        CebankUserLogonOpForSign =
                "opName=CebankUserLogon1_1Op"
                        + split
                        + "reqData="
                        + BOS_URLencode(SignOpStep.getNodeValue((new SignOpStep("<?xml version=\"1.0\" encoding=\"GBK\"?>"
                        + "<BOSEBankData>"
                        + "<opReq>"
                        + "<serialNo>"
                        + serialNo
                        + "</serialNo>"
                        + "<reqTime>"
                        + "20080904"
                        + "</reqTime>"
                        + "<ReqParam>"
                        + "<userID>"
                        //20100804 S
//                + "666042200398268"
                        + "174704"
                        //20100804 E
                        + "</userID>"
                        + "<userPWD>"
                        + "962888"
                        + "</userPWD>" + "</ReqParam>"
                        + "</opReq>" + "</BOSEBankData>")).sign(), "signed_data"));
        longonresult = logon(ip, port);
        System.out.println("第" + "个帐号登陆结果：=" + longonresult + "=");
        if (longonresult.substring(0, 1).equals("<")) {// 没有
            System.out.println("第" + "个帐号没有登陆正确:" + longonresult);
            return "";
        } else {
            sessionId = longonresult.substring(0, 40);

            return sessionId;
        }
    }

    //登录
    public static String getSessionId(String ip, String port, String uname, String pass) {
        String longonresult = null;
        String result = null;
        BankUtil.ipAddress = ip;
        String serialNo = String.valueOf(Math.round(Math.random() * 10000))
                + System.currentTimeMillis();
        CebankUserLogonOpForSign =
                "opName=CebankUserLogon1_1Op"
                        + split
                        + "reqData="
                        + BOS_URLencode(SignOpStep.getNodeValue((new SignOpStep("<?xml version=\"1.0\" encoding=\"GBK\"?>"
                        + "<BOSEBankData>"
                        + "<opReq>"
                        + "<serialNo>"
                        + serialNo
                        + "</serialNo>"
                        + "<reqTime>"
                        + "20080904"
                        + "</reqTime>"
                        + "<ReqParam>"
                        + "<userID>"
                        //20100804 S
//                + "666042200398268"
                        + uname
                        //20100804 E
                        + "</userID>"
                        + "<userPWD>"
                        + pass
                        + "</userPWD>" + "</ReqParam>"
                        + "</opReq>" + "</BOSEBankData>")).sign(), "signed_data"));
        longonresult = logon(ip, port);
        System.out.println("第" + "个帐号登陆结果：=" + longonresult + "=");
        if (longonresult.substring(0, 1).equals("<")) {// 没有
            System.out.println("第" + "个帐号没有登陆正确:" + longonresult);
            return "";
        } else {
            sessionId = longonresult.substring(0, 40);

            return sessionId;
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
}
