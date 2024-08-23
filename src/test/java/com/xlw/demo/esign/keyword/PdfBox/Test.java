package com.xlw.demo.esign.keyword.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        String pdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        File file = new File(pdfPath);
        PDDocument doc = PDDocument.load(file);

        String keyWords = "反商业贿赂条款";
        PDImageXObject stampImg = PDImageXObject.createFromFile("/Users/xieliwei/Desktop/电子签章测试/益通数科章.png", doc);
        PdfBoxKeyWordPosition pdf = new PdfBoxKeyWordPosition(keyWords, pdfPath);

        PDPageContentStream contentStream = null;
        List<float[]> list = pdf.getCoordinate();
        // 多页pdf的处理
        for (float[] fs : list) {
            PDPage page = doc.getPage((int) fs[2] - 1);
            float x = fs[0];
            float y = fs[1];
            contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.drawImage(stampImg, x, y);
            contentStream.close();
        }
        doc.save("sign_finish.pdf");
        doc.close();
    }

}
