package com.xlw.demo.esign;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;  
import org.apache.pdfbox.pdmodel.PDPageContentStream;  
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;  
import org.apache.pdfbox.text.PDFTextStripper;  
  
import java.io.File;  
import java.io.IOException;  
import java.util.List;  
  
public class PdfBoxStampImageOnKeyword {  
  
    // 假设的搜索和盖章方法  
    private static void stampImageOnKeyword(PDDocument document, String keyword, String imagePath) throws IOException {  
        // 加载图片章  
        PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);  
  
        // 遍历每一页  
        for (PDPage page : document.getPages()) {  
            // 这里应该有一个方法来搜索页面上的文本并找到关键字的位置  
            // 但为了简化，我们假设关键字在第一页的某个位置（例如(100, 700)）  
            // 在实际应用中，你需要实现文本搜索逻辑
  
            // 假设的关键字位置（这里应该是通过搜索得到的）  
            float keywordX = 100;  
            float keywordY = 700;  
  
            // 计算盖章的位置（这里简单地放在关键字旁边）  
            float imageX = keywordX + 50; // 关键字右侧50单位  
            float imageY = keywordY - 50; // 关键字上方50单位（根据需要调整）  
  
            // 盖章  
            stampImage(document, page, imageX, imageY, pdImage);  
        }  
    }  
  
    // 盖章方法  
    private static void stampImage(PDDocument document, PDPage page, float x, float y, PDImageXObject pdImage) throws IOException {  
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {  
            // 绘制图片（注意：这里的宽度和高度应该根据图片的实际尺寸和需要来调整）  
            contentStream.drawImage(pdImage, x, y, pdImage.getWidth() / 2, pdImage.getHeight() / 2); // 假设缩放图片到一半大小  
        }  
    }  
  
    public static void main(String[] args) {  
        String inputPdfPath = "/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf";
        String outputPdfPath = "/Users/xieliwei/Desktop/电子签章测试/output.pdf";
        String imagePath = "/Users/xieliwei/Desktop/电子签章测试/益通数科章.png"; // 图片章路径
        String keyword = "电子签章/签名";
  
        try (PDDocument document = PDDocument.load(new File(inputPdfPath))) {  
            stampImageOnKeyword(document, keyword, imagePath);  
            document.save(outputPdfPath);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    // 注意：上面的代码示例中并没有实现基于关键字的精确搜索  
    // 你需要实现自己的搜索逻辑来找到关键字的确切位置  
    // 这通常涉及到解析PDF的文本内容（如使用PDFTextStripper及其派生类）  
    // 并处理返回的文本和可能的位置信息（但请注意，PDFTextStripper本身不直接提供位置信息）  
}