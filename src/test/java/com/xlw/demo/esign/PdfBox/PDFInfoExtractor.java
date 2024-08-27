package com.xlw.demo.esign.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFInfoExtractor {

    public static void main(String[] args) {
        File pdfFile = new File("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf");

        try (PDDocument document = PDDocument.load(pdfFile)) {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);

                // 定义正则表达式  
                Pattern patternSupplier = Pattern.compile("供货方：(.*)");
                Pattern patternBuyer = Pattern.compile("购货方：(.*)");
                Pattern patternOrderNumber = Pattern.compile("订单编号：(.*)");

                // 使用正则表达式搜索并提取信息  
                Matcher matcherSupplier = patternSupplier.matcher(text);
                if (matcherSupplier.find()) {
                    System.out.println("供货方：" + matcherSupplier.group(1).trim());
                }

                Matcher matcherBuyer = patternBuyer.matcher(text);
                if (matcherBuyer.find()) {
                    System.out.println("购货方：" + matcherBuyer.group(1).trim());
                }

                Matcher matcherOrderNumber = patternOrderNumber.matcher(text);
                if (matcherOrderNumber.find()) {
                    System.out.println("订单编号：" + matcherOrderNumber.group(1).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}