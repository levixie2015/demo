package com.xlw.demo.esign.PdfBox;

import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;

// 自定义SignatureInterface，使用ExternalSigningSupport
public class ExternalSignatureImplementation implements SignatureInterface, ExternalSigningSupport {
    private PrivateKey privateKey;

    public ExternalSignatureImplementation(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public byte[] sign(InputStream inputStream) throws IOException {
        // 这里调用你的外部签名方法
        byte[] bytes = toByteArray(inputStream);
        return signHashExternally(bytes, privateKey);
    }

    // ExternalSigningSupport的方法
    public void modifySigningDictionary(PDSignature signature) {
        // 可以修改签名字典（如果需要的话）  
    }

    // 假设你有一个方法来执行外部签名
    private byte[] signHashExternally(byte[] hash, PrivateKey privateKey) {
        // 这里应该调用你的外部签名服务或库
        // 返回签名字节
        // 注意：这只是一个示例方法，你需要实现它
        return new byte[0]; // 返回一个空的字节数组作为示例
    }

    /**
     * 将InputStream转换为byte数组。
     *
     * @param inputStream 输入流
     * @return 输入流内容的byte数组
     * @throws IOException 如果读取输入流时发生错误
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

    @Override
    public InputStream getContent() throws IOException {
        return null;
    }

    @Override
    public void setSignature(byte[] bytes) throws IOException {

    }
}