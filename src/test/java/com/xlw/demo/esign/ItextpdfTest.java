package com.xlw.demo.esign;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ItextpdfTest {
    /**
     * 切割图片
     *
     * @param path 图片路径
     * @param n    切割份数
     * @return itextPdf的Image[]
     * @throws IOException
     * @throws BadElementException
     */
    public static Image[] slicingImages(String path, int n) throws IOException, BadElementException {
        Image[] nImage = new Image[n];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage img = ImageIO.read(new File(path));
        int h = img.getHeight();
        int w = img.getWidth();

        int sw = w / n;
        for (int i = 0; i < n; i++) {
            BufferedImage subImg;
            if (i == n - 1) {//最后剩余部分
                subImg = img.getSubimage(i * sw, 0, w - i * sw, h);
            } else {//前n-1块均匀切
                subImg = img.getSubimage(i * sw, 0, sw, h);
            }

            ImageIO.write(subImg, path.substring(path.lastIndexOf('.') + 1), out);
            nImage[i] = Image.getInstance(out.toByteArray());
            out.flush();
            out.reset();
        }
        return nImage;
    }

    /**
     * 盖骑缝章
     *
     * @param infilePath  原PDF路径
     * @param outFilePath 输出PDF路径
     * @param picPath     章图片路径
     * @throws IOException
     * @throws DocumentException
     */
    public static void stamperCheckMarkPDF(String infilePath, String outFilePath, String picPath) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(infilePath);//选择需要印章的pdf
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outFilePath));//加完印章后的pdf

        Rectangle pageSize = reader.getPageSize(1);//获取第一页（索引从1开始）
        float height = pageSize.getHeight();
        float width = pageSize.getWidth();

        int nums = reader.getNumberOfPages();
        Image[] nImage = slicingImages(picPath, nums);//生成骑缝章切割图片


        for (int n = 1; n <= nums; n++) {
            PdfContentByte over = stamp.getOverContent(n);//设置在第几页打印印章
            Image img = nImage[n - 1];//选择图片
            img.setAbsolutePosition(width - img.getWidth(), height / 2 - img.getHeight() / 2);//控制图片位置
            over.addImage(img);
        }
        stamp.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        String infilePath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        String outfilePaht = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司_签章Itextpdf.pdf";
        String picPath = "/Users/xieliwei/Desktop/电子签章测试/测试章.png";
        stamperCheckMarkPDF(infilePath, outfilePaht, picPath);
    }
}