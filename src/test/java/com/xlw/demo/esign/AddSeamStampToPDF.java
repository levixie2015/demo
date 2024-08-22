package com.xlw.demo.esign;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
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

//            List<PDImageXObject> pdImageXObjectList = slicingImages(document, "/Users/xieliwei/Desktop/电子签章测试/测试章.png", document.getPages().getCount());//生成骑缝章切割图片
            List<PDImageXObject> pdImageXObjectList = slicingImages2(document, "/Users/xieliwei/Desktop/电子签章测试/测试章.png", document.getPages().getCount());//生成骑缝章切割图片

            // 遍历PDF的每一页
            for (int i = 0; i < document.getPages().getCount(); i++) {
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

                    PDImageXObject img = pdImageXObjectList.get(i);

                    // 这里我们简单地在每页上绘制相同的图像  
                    contents.drawImage(img, imgX, imgY, img.getWidth(), img.getHeight());
//                    contents.drawImage(pdImageXObjectList.get(i), imgX, imgY);
                }
            }

            // 保存修改后的PDF  
            document.save("/Users/xieliwei/Desktop/电子签章测试/output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<PDImageXObject> slicingImages(PDDocument document, String path, int n) throws IOException {
        List<PDImageXObject> pdImageXObjectList = new ArrayList<>();

        BufferedImage img = ImageIO.read(new File(path));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int w = img.getWidth();
        int h = img.getHeight();

        int sw = w / n;
        for (int i = 0; i < n; i++) {
            BufferedImage subImg;
            if (i == n - 1) {//最后剩余部分
                subImg = img.getSubimage(i * sw, 0, w - i * sw, h);
            } else {//前n-1块均匀切
                subImg = img.getSubimage(i * sw, 0, sw, h);
            }
            String formatName = path.substring(path.lastIndexOf('.') + 1);//文件名称
            ImageIO.write(subImg, formatName, out);
            PDImageXObject pDImageXObject = PDImageXObject.createFromByteArray(document, out.toByteArray(), formatName);
            pdImageXObjectList.add(pDImageXObject);

            out.flush();
            out.reset();
        }
        return pdImageXObjectList;
    }


    private static List<PDImageXObject> slicingImages2(PDDocument document, String path, int n) throws IOException {
        List<PDImageXObject> pdImageXObjectList = new ArrayList<>();
        BufferedImage[] images = cutImage(document, path, n);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //将图片绘制到PDF页面上的指定位置
        for (int i = 0; i < n; i++) {
            BufferedImage image = images[i];
            String formatName = path.substring(path.lastIndexOf('.') + 1);//文件名称
            ImageIO.write(image, formatName, out);
            PDImageXObject pDImageXObject = PDImageXObject.createFromByteArray(document, out.toByteArray(), formatName);
            pdImageXObjectList.add(pDImageXObject);
            out.flush();
            out.reset();
        }
        return pdImageXObjectList;
    }

    /**
     * 定义GetImage方法，根据PDF页数分割印章图片
     *
     * @param num
     * @return
     * @throws IOException
     */
    public static BufferedImage[] cutImage(PDDocument document, String path, int num) throws IOException {
        String originalImg = "/Users/xieliwei/Desktop/电子签章测试/测试章.png";
        BufferedImage image = ImageIO.read(new File(originalImg));

        int rows = 1;
        int cols = num;

        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols;

        int chunkHeight = image.getHeight() / rows;

        int count = 0;

        BufferedImage[] imgs = new BufferedImage[chunks];

        for (int x = 0; x < rows; x++) {

            for (int y = 0; y < cols; y++) {

                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                Graphics2D gr = imgs[count++].createGraphics();

                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight,

                        chunkWidth * y, chunkHeight * x,

                        chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, Color.WHITE, null);

                gr.dispose();
            }
        }
        return imgs;
    }
}