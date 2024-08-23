package com.xlw.demo.esign;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PdfBoxStampTest {

    /**
     * 使用PDFBox库来实现PDF文件的盖章
     */
    @Test
    public void PdfBoxStampTest() {
        try (PDDocument document = PDDocument.load(new File("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf"))) {
            // 加载印章图像
            PDImageXObject pdImage = PDImageXObject.createFromFile("/Users/xieliwei/Desktop/电子签章测试/益通数科章.png", document);

            // 获取第一页（索引从0开始）
            PDPage page = document.getPage(0);

            // 创建一个新的内容流来添加内容
            try (PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                // 设置印章图像的位置和尺寸
                // 注意：PDFBox的坐标系统左下角为原点(0,0)，向右为x轴正方向，向上为y轴正方向
                float x = 100; // 印章的x坐标
                float y = 700; // 印章的y坐标（根据页面大小调整）
                // 假设印章图像不需要缩放
                float width = pdImage.getWidth();
                float height = pdImage.getHeight();

                // 将印章图像添加到PDF页面
                contents.drawImage(pdImage, x, y, width * 0.5f, height * 0.5f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 保存修改后的PDF
            document.save("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司_签章.pdf");
            document.close();
            System.out.println("PDF stamped successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
