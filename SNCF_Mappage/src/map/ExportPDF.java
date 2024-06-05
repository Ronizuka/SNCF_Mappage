package map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportPDF {

    public static void exportPlanToPDF(JFrame frame, DrawingArea drawingArea) {
        FileDialog fileDialog = new FileDialog(frame, "Choisir un fichier pour enregistrer le PDF", FileDialog.SAVE);
        fileDialog.setFile("*.pdf");
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();

        if (directory != null && file != null) {
            String pdfPath = directory + file;
            if (!pdfPath.toLowerCase().endsWith(".pdf")) {
                pdfPath += ".pdf";
            }

            try {
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                document.open();

                drawPlanToPDF(document, writer, drawingArea);

                document.close();
                JOptionPane.showMessageDialog(frame, "Plan exporté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'exportation du plan.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void drawPlanToPDF(Document document, PdfWriter writer, DrawingArea drawingArea) {
        PdfContentByte cb = writer.getDirectContent();
        PdfTemplate template = cb.createTemplate(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        Graphics2D g2d = template.createGraphics(template.getWidth(), template.getHeight());

        double scaleX = template.getWidth() / drawingArea.getWidth();
        double scaleY = template.getHeight() / drawingArea.getHeight();
        double scale = Math.min(scaleX, scaleY);

        g2d.scale(scale, scale);
        drawingArea.printAll(g2d);

        g2d.dispose();
        cb.addTemplate(template, 0, 0);
    }
}