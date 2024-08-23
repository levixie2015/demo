package com.xlw.demo.esign.PdfBox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfBoxStampTest {
    public static void main(String[] args) {
        String pdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        String stampImgPath = "/Users/xieliwei/Desktop/电子签章测试/益通数科章.png";
        String keyWords = "电子签章";

        //根据关键字图片盖章
        float xOffset = -100f;//印章的x坐标偏移量
        float yOffset = -90f;//印章的y坐标偏移量
        float widthScale = 0.5f;//印章宽度缩放比例
        float heightScale = 0.5f;//印章高度缩放比例
        boolean compress = true;//参数决定了写入的内容是否应该被压缩。如果设置为true，则PDFBox会尝试压缩内容以减少文件大小；如果设置为false，则内容将以未压缩的形式写入。通常，启用压缩是一个好主意，因为它可以减少生成的PDF文件的大小
//        stampByKeyWords(pdfPath, stampImgPath, keyWords, xOffset, yOffset, widthScale, heightScale, compress);

        //根据绝对位置图片盖章
        float x = 49.182f-100f; // 印章的x坐标
        float y = 724.82f-90f; // 印章的y坐标（根据页面大小调整）
        stampByAbsolutePosition(pdfPath, stampImgPath, x, y, widthScale, heightScale, compress);
    }

    /**
     * 图片盖章
     *
     * @param pdfPath      pdf文档路径
     * @param stampImgPath 图片印章路径
     * @param keyWords     关键字
     * @param xOffset      印章的x坐标偏移量
     * @param yOffset      印章的y坐标偏移量
     * @param widthScale   印章宽度缩放比例
     * @param heightScale  印章高度缩放比例
     * @param compress     参数决定了写入的内容是否应该被压缩。如果设置为true，则PDFBox会尝试压缩内容以减少文件大小；如果设置为false，则内容将以未压缩的形式写入。通常，启用压缩是一个好主意，因为它可以减少生成的PDF文件的大小
     */
    public static void stampByKeyWords(String pdfPath, String stampImgPath, String keyWords, float xOffset, float yOffset, float widthScale, float heightScale, boolean compress) {
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
                contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, compress, true);
                contentStream.drawImage(stampImg, x, y, stampImg.getWidth() * widthScale, stampImg.getHeight() * heightScale);
                contentStream.close();
            }
            doc.save("sign_finish.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据绝对位置图片盖章
     *
     * @param pdfPath      pdf文档路径
     * @param stampImgPath 图片印章路径
     * @param x            印章的x坐标
     * @param y            印章的y坐标
     * @param widthScale   印章宽度缩放比例
     * @param heightScale  印章高度缩放比例
     * @param compress     参数决定了写入的内容是否应该被压缩。如果设置为true，则PDFBox会尝试压缩内容以减少文件大小；如果设置为false，则内容将以未压缩的形式写入。通常，启用压缩是一个好主意，因为它可以减少生成的PDF文件的大小
     */
    public static void stampByAbsolutePosition(String pdfPath, String stampImgPath, float x, float y, float widthScale, float heightScale, boolean compress) {
        try (PDDocument doc = PDDocument.load(new File(pdfPath))) {
            PDImageXObject stampImg = PDImageXObject.createFromFile(stampImgPath, doc);

            //遍历pdf文件
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                //最后一页（索引从0开始）
//                if (i == doc.getNumberOfPages() - 1) {
                if (i == 2) {
                    PDPage page = doc.getPage(i);

                    // 创建一个新的内容流来添加内容
                    try (PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, compress, true)) {
                        // 设置印章图像的位置和尺寸
                        // 注意：PDFBox的坐标系统左下角为原点(0,0)，向右为x轴正方向，向上为y轴正方向
                        // 假设印章图像不需要缩放
                        float width = stampImg.getWidth();
                        float height = stampImg.getHeight();

                        // 将印章图像添加到PDF页面
                        contents.drawImage(stampImg, x, y, width * widthScale, height * heightScale);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    doc.save("sign_finish.pdf");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
