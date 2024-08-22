package com.xlw.demo.esign;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Apache PDFBox中实现骑缝图章（即跨越多页的印章或水印）
 */
public class AddSeamStampToPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf"))) {
            // 加载骑缝图章图像  
            PDImageXObject stampImage = PDImageXObject.createFromFile("/Users/xieliwei/Desktop/电子签章测试/测试章.png", document);

            List<PDImageXObject> pdImageXObjectList = cutPDImageXObjectList(document, "/Users/xieliwei/Desktop/电子签章测试/测试章.png", document.getPages().getCount());//生成骑缝章切割图片

            // 遍历PDF的每一页
            for (int i = 0; i < document.getPages().getCount() - 1; i++) {
                PDPage page = document.getPage(i);
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
                    contents.drawImage(pdImageXObjectList.get(i), imgX, imgY, stampImage.getWidth(), stampImage.getHeight());
                }
            }

            // 保存修改后的PDF  
            document.save("output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<PDImageXObject> cutPDImageXObjectList(PDDocument document, String stampImgPath, int numBlocks) throws IOException {
        List<BufferedImage> bufferedImages = cutImages(stampImgPath, numBlocks);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<PDImageXObject> pdImageXObjectList = new ArrayList<>();
        for (BufferedImage img : bufferedImages) {
            String formatName = stampImgPath.substring(stampImgPath.lastIndexOf('.') + 1);//文件名称
            ImageIO.write(img, formatName, out);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, out.toByteArray(), formatName);
            pdImageXObjectList.add(pdImage);
        }
        return pdImageXObjectList;
    }

    private static List<BufferedImage> cutImages(String originalImg, int numBlocks) throws IOException {
        BufferedImage image = ImageIO.read(new File(originalImg));

        List<BufferedImage> cutImages = new ArrayList<>();
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        int blockSizeWidth = originalWidth / (numBlocks - 1); // 如果numBlocks > 1
        int blockSizeHeight = originalHeight; // 假设整幅图像高度相同

        for (int i = 0; i < numBlocks - 1; i++) {
            int x = i * blockSizeWidth;
            BufferedImage cutImg = image.getSubimage(x, 0, blockSizeWidth, blockSizeHeight);
            cutImages.add(cutImg);
        }

        // 处理剩余部分
        int remainingWidth = originalWidth - (numBlocks - 1) * blockSizeWidth;
        BufferedImage lastBlock = image.getSubimage(originalWidth - remainingWidth, 0, remainingWidth, blockSizeHeight);
        cutImages.add(lastBlock);

        return cutImages;
    }
}