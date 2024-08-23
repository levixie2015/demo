package com.xlw.demo.esign.keyword.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        String pdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        String stampImgPath = "/Users/xieliwei/Desktop/电子签章测试/益通数科章.png";
        String keyWords = "电子签章";

        float xOffset = -100f;//印章的x坐标偏移量
        float yOffset = -90f;//印章的y坐标偏移量
        float widthScale = 0.5f;//印章宽度缩放比例
        float heightScale = 0.5f;//印章高度缩放比例

        try (PDDocument doc = PDDocument.load(new File(pdfPath))) {
            PDImageXObject stampImg = PDImageXObject.createFromFile(stampImgPath, doc);
            PDFTextStripperByKeyWord keyWordPosition = new PDFTextStripperByKeyWord(keyWords, pdfPath);

            PDPageContentStream contentStream = null;
            List<float[]> keyWordPositionList = keyWordPosition.getCoordinate();

            // 多页pdf的处理
            for (float[] position : keyWordPositionList) {
                PDPage page = doc.getPage((int) position[2] - 1);
                float x = position[0] + xOffset;
                float y = position[1] + yOffset;
                contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
                contentStream.drawImage(stampImg, x, y, stampImg.getWidth() * widthScale, stampImg.getHeight() * heightScale);
                contentStream.close();
            }
            doc.save("sign_finish.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
