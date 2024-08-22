package com.xlw.demo.esign;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

/**
 * Apache PDFBox中实现骑缝图章（即跨越多页的印章或水印）
 */
public class AddSeamStampToPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf"))) {
            // 加载骑缝图章图像  
            PDImageXObject stampImage = PDImageXObject.createFromFile("/Users/xieliwei/Desktop/电子签章测试/测试章.png", document);

            // 遍历PDF的每一页  
            for (PDPage page : document.getPages()) {
                // 获取页面的宽度和高度（注意：这里获取的是点（pt）单位，1英寸=72点）
                float width = page.getMediaBox().getWidth();
                float height = page.getMediaBox().getHeight();

                // 设置图像在页面上的绝对位置
                // 这里假设我们想要将图像放置在页面的右下角，但稍微向上和向左偏移一些
                float imgX = width - stampImage.getWidth();
                float imgY = height / 2 - stampImage.getHeight() / 2; // 这个位置其实是页面中心偏上，但我们可以根据需要调整

                // 如果你想要图像在页面的右下角，可以这样做：
                // float imgX = width - img.getWidth() - 10; // 起始x坐标。假设向右偏移10点
                // float imgY = height - img.getHeight() - 10; // 起始y坐标。假设向下偏移10点

                try (PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    // 这里需要根据跨页效果调整imgX和imgY的值
                    // 例如，如果骑缝图章需要跨越两页，并且第一页显示上半部分，第二页显示下半部分并重叠一些  
                    // 你可能需要在第二页上减少y的值并重新计算x以确保对齐  

                    // 这里我们简单地在每页上绘制相同的图像  
                    contents.drawImage(stampImage, imgX, imgY, stampImage.getWidth(), stampImage.getHeight());

                    // 注意：这里没有实现跨页对齐和重叠，你需要自己添加逻辑来实现这一点  
                }
            }

            // 保存修改后的PDF  
            document.save("output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}