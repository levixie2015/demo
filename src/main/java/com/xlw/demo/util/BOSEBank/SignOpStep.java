package com.xlw.demo.util.BOSEBank;

//
//Copyright (C) 2005 ECC CORPORATION
//
//ALL RIGHTS RESERVED BY ECC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS
//FURNISHED BY ECC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ECC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//       ECC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//
//$Log: SignOpStep.java,v $
//Revision 1.3  2008/09/05 03:02:48  zhougy
//*** empty log message ***
//
//Revision 1.2  2007/11/30 06:07:24  zhougy
//修改示例程序
//


import sun.misc.BASE64Decoder;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SignOpStep {
    String verifyValue = null;


    SignOpStep(String verifyValue) {

        this.verifyValue = verifyValue;
        // TODO Auto-generated constructor stub
    }

    public String sign() {
        // TODO Auto-generated method stub
        InetAddress addr = null;
        Socket socket = null;
        String verifyheader = null;
        try {
            verifyheader = "<?xml version=\"1.0\" encoding=\"gbk\"?>\n"
                    + "<msg>\n"
                    + "<msg_head>\n"
                    + "<msg_type>0</msg_type>\n"
                    + "<msg_id>1005</msg_id>\n"
                    + "<msg_sn>0</msg_sn>\n"
                    + "<version>1</version>\n"
                    + "</msg_head>\n"
                    + "<msg_body>\n"
                    + "<origin_data_len>" + this.verifyValue.getBytes("GBK").length + "</origin_data_len>\n"
                    + "<origin_data>" + this.verifyValue + "</origin_data>\n"
                    + "</msg_body>\n"
                    + "</msg>";
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {

            String verifyIp = "127.0.0.1";
            int verifyPort = 8010;
            //verifyValue=getSendStr(verifyValue);
            //System.out.println("发送的原文包："+verifyValue);
            addr = InetAddress.getByName(verifyIp);
            socket = new Socket(addr, verifyPort);
            BufferedWriter wr =
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream(), "GBK"));
            wr.write(verifyheader);
            wr.flush();
            //接收
            BufferedReader rd =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), "GBK"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                // System.out.println(line);
                sb.append(line);
            }
            //处理返回
            String returnStr = sb.toString();
            //System.out.println("返回的密文："+returnStr);


            wr.close();
            rd.close();
            socket.close();
            return returnStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getNodeValue(String returnStr, String tagName) {
        int startIdx, endIdx;
        String beginTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        startIdx = returnStr.indexOf(beginTag) + beginTag.length();
        endIdx = returnStr.indexOf(endTag, startIdx);
        return returnStr.substring(startIdx, endIdx);
    }


    public static byte[] base64Decoder(String bytes) {
        if (bytes == null || bytes.length() < 1)
            return "".getBytes();
        byte[] base64Str = null;
        try {
            base64Str = new BASE64Decoder().decodeBuffer(bytes);
        } catch (Exception e) {
            return "".getBytes();
        }
        return base64Str;
    }

    /**
     * <b>功能描述: </b><br>
     * <p></p>
     *
     * @param args
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        // String otest=args[0];
//        String otest="要签名的数据";
//        SignOpStep ss=new SignOpStep(otest);
        PKCS7 p7;
        ContentInfo info;
//        try {
//            String returnStr=ss.sign();
//            String miwen=getNodeValue(returnStr,"signed_data");
//            System.out.println("返回的密文："+miwen);
//            System.out.println("返回的密文长度："+miwen.length());
//            
//            p7 = new PKCS7(SignOpStep.base64Decoder(miwen));
//            info = p7.getContentInfo();          
//                        
//            String minwen=new String(info.getContentBytes(),"gbk");
//            System.out.println("返回的明文："+minwen);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        p7 = new PKCS7(SignOpStep.base64Decoder("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"));
        info = p7.getContentInfo();

        String minwen = new String(info.getContentBytes(), "gbk");
        System.out.println("返回的明文：" + minwen);
    }

}
