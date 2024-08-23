package com.xlw.demo.esign.PdfBox;

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

public class PdfBoxStampTest {
    public static void main(String[] args) {
        String pdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        String stampImgPath = "/Users/xieliwei/Desktop/电子签章测试/测试章.png";
        String keyWords = "电子签章";

        //根据关键字图片盖章
        float xOffset = -100f;//印章的x坐标偏移量
        float yOffset = -90f;//印章的y坐标偏移量
        float widthScale = 1f;//印章宽度缩放比例
        float heightScale = 1f;//印章高度缩放比例
        boolean compress = true;//参数决定了写入的内容是否应该被压缩。如果设置为true，则PDFBox会尝试压缩内容以减少文件大小；如果设置为false，则内容将以未压缩的形式写入。通常，启用压缩是一个好主意，因为它可以减少生成的PDF文件的大小
//        stampByKeyWords(pdfPath, stampImgPath, keyWords, xOffset, yOffset, widthScale, heightScale, compress);

        //根据绝对位置图片盖章
        float x = 49.182f - 40f; // 印章的x坐标
        float y = 724.82f - 90f; // 印章的y坐标（根据页面大小调整）
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

            List<PDImageXObject> pdImageXObjectList = slicingImages(doc, stampImgPath, doc.getNumberOfPages());//生成骑缝章切割图片
//            List<PDImageXObject> pdImageXObjectList = slicingImages2(doc, stampImgPath, doc.getPages().getCount());//生成骑缝章切割图片

            //遍历pdf文件
            for (int i = 0; i < doc.getPages().getCount(); i++) {
                //最后一页（索引从0开始）
//                if (i == doc.getNumberOfPages() - 1) {

                PDPage page = doc.getPage(i);
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                // 创建一个新的内容流来添加内容
                try (PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, compress, true)) {
                    // 设置印章图像的位置和尺寸
                    // 注意：PDFBox的坐标系统左下角为原点(0,0)，向右为x轴正方向，向上为y轴正方向
                    // 假设印章图像不需要缩放
                    float stampWidth = stampImg.getWidth();
                    float stamHeight = stampImg.getHeight();

                    //设置骑缝章
                    PDImageXObject perforationImg = pdImageXObjectList.get(i);
                    float perforationImgX = pageWidth - perforationImg.getWidth();
                    float perforationImgY = pageHeight / 2 - perforationImg.getHeight() / 2; // 这个位置其实是页面中心偏上，但我们可以根据需要调整
                    contents.drawImage(perforationImg, perforationImgX, perforationImgY, perforationImg.getWidth(), perforationImg.getHeight());

                    if (i == 2) {
                        // 将印章图像添加到PDF页面
                        contents.drawImage(stampImg, x, y, stampWidth * widthScale, stamHeight * heightScale);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                doc.save("sign_finish.pdf");
            }
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
        BufferedImage[] images = cutImage(path, n);
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
    private static BufferedImage[] cutImage(String path, int num) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));

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
