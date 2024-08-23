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

        String keyWords = "电子签章";
        PDImageXObject stampImg = PDImageXObject.createFromFile("/Users/xieliwei/Desktop/电子签章测试/益通数科章.png", doc);
        PDFTextStripperByKeyWord keyWordPosition = new PDFTextStripperByKeyWord(keyWords, pdfPath);

        PDPageContentStream contentStream = null;
        List<float[]> keyWordPositionList = keyWordPosition.getCoordinate();

        float xOffset = -100f;
        float yOffset = -90f;

        // 多页pdf的处理
        for (float[] position : keyWordPositionList) {
            PDPage page = doc.getPage((int) position[2] - 1);
            float x = position[0] + xOffset;
            float y = position[1] + yOffset;
            contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.drawImage(stampImg, x, y, stampImg.getWidth() / 2, stampImg.getHeight() / 2);
            contentStream.close();
        }
        doc.save("sign_finish.pdf");
        doc.close();
    }

}
