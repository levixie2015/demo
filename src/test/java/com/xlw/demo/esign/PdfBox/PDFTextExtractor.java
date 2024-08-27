package com.xlw.demo.esign.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFTextExtractor {

    public static String extractText(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
            return "Document is encrypted";
        }
    }

    public static void searchText(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            System.out.println("Found: " + matcher.group(1));
        }
    }

    public static void main(String[] args) {
        try {
            String pdfText = PDFTextExtractor.extractText("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf");
            searchText(pdfText, "供货方：(.*)");

            searchText(pdfText, "购货方：(.*)");

            searchText(pdfText, "订单编号：(.*)");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}