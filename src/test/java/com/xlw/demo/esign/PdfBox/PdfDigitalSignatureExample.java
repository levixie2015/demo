package com.xlw.demo.esign.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;

import java.io.File;
import java.io.IOException;

public class PdfDigitalSignatureExample {
    public static void main(String[] args) throws IOException {
        PDDocument document = PDDocument.load(new File("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司PdfBox.pdf"));

        //创建PDSignature对象
        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        // 设置其他属性，如签名名称、位置等
        signature.setName("Signer Name");
        signature.setLocation("Location");
        signature.setReason("Reason for signing");

        // 在文档的最后一个页面上添加签名
        PDPage lastPage = document.getPage(document.getNumberOfPages() - 1);
        signature.setByteRange(new int[]{100, 600, 200, 100}); // 签名矩形位置和大小

        // 将签名添加到签名字典
        document.addSignature(signature);
        document.save("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司PdfBox_签名.pdf");
        document.close();
    }
}
