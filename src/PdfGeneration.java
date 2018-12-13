import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;

public class PdfGeneration {
    public void pdfGeneration(String fileName, ObservableList<Person> data) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Image image1 = Image.getInstance(getClass().getResource("/logo.jpg"));
            image1.scaleToFit(800, 150);
            image1.setAbsolutePosition(150f, 650f);
            document.add(image1);

            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n"));

            float[] columnWidths = {1, 3, 3, 4};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("KontaktLista"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("id");
            table.addCell("Vezetéknév");
            table.addCell("Keresztnév");
            table.addCell("E-mail cím");

            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            for(int i = 1; i<=data.size();i++){
                Person actualPerson = data.get(i-1);
                table.addCell(String.valueOf(i));
                table.addCell(actualPerson.getLastName());
                table.addCell(actualPerson.getFirstName());
                table.addCell(actualPerson.getEmail());
            }

            document.add(table);

            Chunk signature = new Chunk("\n\n Generálva a Telefonkönyv alkalmazás segítségével");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }
}
