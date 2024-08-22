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

            // 假设骑缝图章在每页的位置（这里需要根据实际情况调整）  
            float x = 100; // 起始x坐标  
            float y = 700; // 起始y坐标（可能需要根据页面高度调整）  

            // 遍历PDF的每一页  
            for (PDPage page : document.getPages()) {
                try (PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    // 这里需要根据跨页效果调整x和y的值  
                    // 例如，如果骑缝图章需要跨越两页，并且第一页显示上半部分，第二页显示下半部分并重叠一些  
                    // 你可能需要在第二页上减少y的值并重新计算x以确保对齐  

                    // 这里我们简单地在每页上绘制相同的图像  
                    contents.drawImage(stampImage, x, y, stampImage.getWidth(), stampImage.getHeight());

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