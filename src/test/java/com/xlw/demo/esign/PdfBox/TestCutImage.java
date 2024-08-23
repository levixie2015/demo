package com.xlw.demo.esign.PdfBox;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class TestCutImage {
    public static void main(String[] args) throws IOException {
        String stampImgPath = "/Users/xieliwei/Desktop/电子签章测试/益通数科章.png";

        Triple<BufferedImage[], Integer, Integer> triple = slicingImages(null, stampImgPath, 5);
        BufferedImage[] bufferedImages = triple.getLeft();
        Integer w = triple.getMiddle();
        Integer h = triple.getRight();

        for (int i = 0; i < bufferedImages.length; i++) {
            BufferedImage bufferedImage = bufferedImages[i];

            // 这里可以添加一些绘图代码，比如使用Graphics2D来绘制图形
            // 仅为示例，我们创建一个简单的红色矩形
            java.awt.Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(java.awt.Color.RED);
            g2d.fillRect(50, 50, 100, 100);
            g2d.dispose();

            // 指定保存图片的文件路径
            String name = "output" + i + ".png";
            File outputfile = new File(name);
            try {
                // 使用ImageIO的write方法将BufferedImage保存为图片文件
                // 第一个参数是BufferedImage对象，第二个参数是图片格式（ImageIO.write支持多种格式），第三个参数是文件
                ImageIO.write(bufferedImage, "PNG", outputfile);
                System.out.println("图片" + i + "保存成功！");
            } catch (IOException e) {
                System.out.println("图片保存失败：" + e.getMessage());
            }
        }
    }

    public static Triple<BufferedImage[], Integer, Integer> slicingImages(PDDocument document, String path, int n) throws IOException {
        BufferedImage[] nImage = new BufferedImage[n];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage img = ImageIO.read(new File(path));
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

            nImage[i] = subImg;
            out.flush();
            out.reset();
        }
        return Triple.of(nImage, w, h);
    }
}
