package com.xlw.demo.util.BOSEBank;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

public class FileTest {

    private static final String ip = "127.0.0.1";
    private static final String port = "7071";

    /**
     * @param fileName :文件绝对路径
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String uploadFile(String fileName) throws UnsupportedEncodingException, IOException {
        String url = "http://" + ip + ":" + port + "/CM/APIUploadUNFileServlet?dse_sessionId=" + BankUtil.sessionId
                + "&serialNo=" + String.valueOf(Math.round(Math.random() * 10000));
        System.out.println(url);
        HttpClient client = new HttpClient();
        GBKPostMethod mPost = new GBKPostMethod(url);
        mPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
        client.setParams(new HttpClientParams() {
            {
                setSoTimeout(300000);
            }
        });
        File file = new File(fileName);
        System.out.println("File = " + file);
        System.out.println("File Length = " + file.length());
        System.out.println("http url = " + url);

        FilePart[] parts = {new FilePart("file12", file)};
        MultipartRequestEntity isre = new MultipartRequestEntity(parts, mPost.getParams());
        mPost.setRequestEntity(isre);
        System.out.println(mPost.getRequestCharSet());
        client.executeMethod(mPost);

        System.out.println("statusLine>>>" + mPost.getStatusLine());
        System.out.println("==================:S");
        System.out.println(new String(mPost.getResponseBodyAsString().getBytes("GBK")));
        System.out.println("==================:E");

        mPost.releaseConnection();

        return new String(mPost.getResponseBodyAsString().getBytes(mPost.getResponseCharSet()), "GBK");
    }

    /*
     * 文件下载
     */
    public static String downloadFile(String batchNo, String serialNo, String type) {
        String url = "http://" + ip + ":" + port + "/CM/APIDownLoadUNFileServlet?dse_sessionId=" + BankUtil.sessionId
                + "&batchNo=" + batchNo + "&serialNo=" + serialNo + "&type=" + type;

        HttpClient client = new HttpClient();
        GetMethod httpGet = new GetMethod(url);
        String fileName = null;
        InputStream in = null;
        BufferedInputStream br = null;
        ByteArrayOutputStream bouts = null;
        FileOutputStream out = null;
        try {
            client.executeMethod(httpGet);
            in = httpGet.getResponseBodyAsStream();
            Header header = httpGet.getResponseHeader("fileName");
            if (header == null || header.getValue() == null || "".equals(header.getValue())) {
                br = new BufferedInputStream(in);
                bouts = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                int len = 0;

                while ((len = br.read(b)) != -1) {
                    bouts.write(b, 0, len);
                }
                bouts.flush();
                return new String(bouts.toByteArray(), "GBK");
            } else {
                fileName = header.getValue();
                File file = new File("c:/" + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new FileOutputStream(file);

                byte[] b = new byte[4096];
                int len = 0;
                br = new BufferedInputStream(in);

                while ((len = br.read(b)) != -1) {
                    out.write(b, 0, len);
                }
                out.flush();
                return "下载文件成功:" + file.getAbsolutePath();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return "下载文件失败:" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "下载文件失败:" + e.getMessage();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bouts != null) {
                try {
                    bouts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpGet.releaseConnection();
        }
    }
}
