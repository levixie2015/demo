package com.xlw.demo.esign.PdfBox;

import cn.hutool.core.io.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestReview {
    public static void main(String[] args) {
        String pdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";  // PDF文件路径
        String outputPath = "/Users/xieliwei/Desktop/电子签章测试/folder";  // 图片输出路径

        pdfToImage(pdfPath, outputPath);
    }

    public static void pdfToImage(String pdfPath, String outputPath) {
        boolean exist = FileUtil.exist(outputPath);
        if (!exist) {
            FileUtil.mkdir(outputPath);
        }
        try {
            // 加载PDF文件
            PDDocument document = PDDocument.load(new File(pdfPath));
            // 创建PDFRenderer对象
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // 遍历PDF的每一页，并将其转换为图片
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                // 渲染第pageIndex页PDF为图片
                BufferedImage image = pdfRenderer.renderImage(pageIndex);
                // 保存图片
                ImageIO.write(image, "PNG", FileUtil.newFile(outputPath + "/page_" + (pageIndex + 1) + ".png"));
            }

            // 关闭PDF文件
            document.close();
            System.out.println("PDF转换为图片成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
