import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PdfGeneration {
    public void pdfGeneration(String fileName, String text) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(200, 86);
            image1.setAbsolutePosition(200f, 750f);
            document.add(image1);

            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n"+text, FontFactory.getFont("betutipus", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            Chunk signature = new Chunk("\n\n Generálva a Telefonkönyv alkalmazás segítségével");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
