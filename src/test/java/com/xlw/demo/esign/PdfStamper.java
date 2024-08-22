package com.xlw.demo.esign;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 假设的文本位置搜索接口  
interface TextPositionFinder {
    // 假设这个方法返回一个包含关键字位置的列表  
    // 这里的位置信息需要根据你的实现来定义  
    // 例如，它可能是一个包含x, y坐标和页面索引的类  
    List<KeywordPosition> findKeywordPositions(PDDocument document, String keyword) throws IOException;
}

// 假设的关键字位置类
@Data
@AllArgsConstructor
class KeywordPosition {
    int pageIndex;
    float x;
    float y;
}

public class PdfStamper {
    public static void main(String[] args) {
        stampKeyword("/Users/xieliwei/Desktop/电子签章测试/瑞康医药集团河北有限公司.pdf", "/Users/xieliwei/Desktop/电子签章测试/output.pdf", "/Users/xieliwei/Desktop/电子签章测试/益通数科章.png", "盖章", new TextPositionFinder() {
            @Override
            public List<KeywordPosition> findKeywordPositions(PDDocument document, String keyword) throws IOException {
                List<KeywordPosition> positions = new ArrayList<>();
                for (int i = 0; i < document.getNumberOfPages(); i++) {
                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition(true);

                    // 这里通常需要设置区域，但在这个简化的例子中，我们遍历整个页面
                    // 在实际应用中，你可能需要更精细地控制区域
                    stripper.getText(document);


                    // 由于 PDFTextStripperByArea 不直接支持文本搜索，我们模拟它
                    // 在实际应用中，你可能需要重写 PDFTextStripper 并使用 LocationTextExtractionStrategy
                    // 但为了简化，我们假设已经有一个包含所有 TextPosition 的列表（这在实际中是不现实的）

                    // 假设的 TextPosition 列表（在实际中，你需要从 PDFTextStripper 或其策略中获取这些）
                    // List<TextPosition> textPositions = ...; // 从某个地方获取

                    // 由于没有实际的 TextPosition 列表，这里我们跳过搜索部分
                    // 并假设我们知道文本的确切位置（这仅用于演示）

                    // 假设我们在第一页的某个位置找到了关键字（这只是一个模拟）
                    if (i == 0) { // 假设关键字在第一页
                        // 在实际应用中，你需要遍历 textPositions 并检查每个 TextPosition 的字符串
                        // 这里我们直接添加一个模拟的位置
                        float x = 100f;
                        float y = 500f;
                        positions.add(new KeywordPosition(i, x, y)); // 假设的 x, y 坐标
                    }
                    // 注意：上面的代码是模拟的，并不实际搜索文本
                    // 在实际应用中，你需要实现文本搜索逻辑
                }
                return positions;
            }
        });
    }

    public static void stampKeyword(String inputPdfPath, String outputPdfPath, String imagePath, String keyword, TextPositionFinder finder) {
        try (PDDocument document = PDDocument.load(new File(inputPdfPath))) {
            // 加载图片章  
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);

            // 搜索关键字位置  
            List<KeywordPosition> positions = finder.findKeywordPositions(document, keyword);

            // 遍历位置并盖章  
            for (KeywordPosition position : positions) {
                PDPage page = document.getPage(position.pageIndex);
                float imageX = position.x + 50; // 假设在关键字右侧50单位处盖章  
                float imageY = position.y - 50; // 假设在关键字上方50单位处盖章  
                stampImage(document, page, imageX, imageY, pdImage);
            }

            // 保存修改后的PDF  
            document.save(outputPdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 盖章方法（与之前相同）  
    private static void stampImage(PDDocument document, PDPage page, float x, float y, PDImageXObject pdImage) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
            contentStream.drawImage(pdImage, x, y, pdImage.getWidth() / 2, pdImage.getHeight() / 2); // 缩放图片  
        }
    }

    // 主方法或调用stampKeyword方法的地方（这里未显示）  

    // 注意：你需要实现TextPositionFinder接口来搜索文本位置  
    // 这通常涉及到解析PDF的文本内容，并可能使用PDFBox的文本位置策略，如LocationTextExtractionStrategy  
}

// ...（TextPositionFinder接口的实现将在这里进行）