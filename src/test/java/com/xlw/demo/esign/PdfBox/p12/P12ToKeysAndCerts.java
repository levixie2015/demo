package com.xlw.demo.esign.PdfBox.p12;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Enumeration;

/**
 * <pre>
 * -  CERT格式通常与DER格式相同，只是文件扩展名不同。有些可能将 .cer 或 .crt 用作证书文件的扩展名，但内容是相同的DER编码的二进制数据。
 * -  PEM格式是一个基于文本的格式，以"-----BEGIN CERTIFICATE-----"开头，并以"-----END CERTIFICATE-----"结尾，内容是Base64编码的DER数据。
 * 原文链接：https://blog.csdn.net/promise524/article/details/140854794
 * </pre>
 */
public class P12ToKeysAndCerts {
    public static void main(String[] args) throws Exception {
        String p12FilePath = "./src/main/resources/mytest.p12"; // P12文件路径
        String password = "123456abc"; // P12文件密码

        // 加载P12文件
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(p12FilePath)) {
            keyStore.load(fis, password.toCharArray());
        }

        // 获取别名
        Enumeration<String> aliases = keyStore.aliases();
        String alias = null;
        if (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
        }

        // 提取私钥和公钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();

        // 提取证书并导出为DER格式
        Certificate certificate = keyStore.getCertificate(alias);
        try (FileOutputStream derFos = new FileOutputStream("output_certificate.der")) {
            derFos.write(certificate.getEncoded());
        }

        // 导出证书为PEM格式
        String pemCertificate = "-----BEGIN CERTIFICATE-----\n" +
                Base64.getEncoder().encodeToString(certificate.getEncoded()) +
                "\n-----END CERTIFICATE-----\n";
        try (FileWriter pemFw = new FileWriter("output_certificate.pem")) {
            pemFw.write(pemCertificate);
        }

        // CERT格式通常与DER相同，只是文件扩展名不同
        try (FileOutputStream certFos = new FileOutputStream("output_certificate.cert")) {
            certFos.write(certificate.getEncoded());
        }

        System.out.println("私钥: " + privateKey);
        System.out.println("公钥: " + publicKey);
        System.out.println("DER格式证书已导出到: output_certificate.der");
        System.out.println("PEM格式证书已导出到: output_certificate.pem");
        System.out.println("CERT格式证书已导出到: output_certificate.cert");
    }
}