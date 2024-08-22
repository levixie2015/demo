package com.xlw.demo.esign;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfGraphicsUnit;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfUnitConvertor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpirePdfTest {

    public static void main(String[] args) throws IOException {

        //加载测试文档
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf");

        //获取分割后的印章图片
        BufferedImage[] images = GetImage(pdf.getPages().getCount());

        float x = 0;
        float y = 0;
        //实例化PdfUnitConvertor类
        PdfUnitConvertor convert = new PdfUnitConvertor();
        PdfPageBase pageBase;

        //将图片绘制到PDF页面上的指定位置
        for (int i = 0; i < pdf.getPages().getCount(); i++) {

            BufferedImage image = images[i];

            pageBase = pdf.getPages().get(i);

            x = (float) pageBase.getSize().getWidth() - convert.convertUnits(image.getWidth(), PdfGraphicsUnit.Point, PdfGraphicsUnit.Pixel) + 40;

            y = (float) pageBase.getSize().getHeight() / 2;

            pageBase.getCanvas().drawImage(PdfImage.fromImage(image), new Point2D.Float(x, y));

        }

        //保存PDF文档
        pdf.saveToFile("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司_签章.pdf");

    }

    //定义GetImage方法，根据PDF页数分割印章图片
    static BufferedImage[] GetImage(int num) throws IOException {

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