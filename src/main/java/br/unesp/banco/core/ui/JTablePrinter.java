package br.unesp.banco.core.ui;

import br.unesp.banco.system.transaction.TransactionType;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JTablePrinter {

    private static File getDestination() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        return fileChooser.getSelectedFile();
    }

    public static void print(String header, JTable jtable) {
        File destination = getDestination();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            String name = "extrato_" + LocalDateTime.now().toString()
                                                    .replaceAll("[.:]", "_");
            File file = new File(String.format("%s/%s.pdf", destination.getAbsolutePath(), name));
            file.createNewFile();

            PdfWriter writer = new PdfWriter(file.getAbsolutePath());
            Document document = new Document(new PdfDocument(writer));

            PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER);

            Paragraph paragraph = new Paragraph(header);
            paragraph.setFont(font);
            paragraph.setFontSize(16);
            document.add(paragraph);

            Paragraph currentInstant = new Paragraph("Data atual: " + formatter.format(LocalDateTime.now()));
            currentInstant.setFont(font);
            currentInstant.setFontSize(12);

            Paragraph last = new Paragraph("Visualizando transações dos últimos 10 dias");
            last.setFont(font);
            last.setFontSize(12);

            document.add(currentInstant);
            document.add(last);

            document.add(new Paragraph());

            float[] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);

            int dateColumn = 0;
            int typeColumn = 1;
            int valueColumn = 2;

            table.addCell(getNewColumn().add("Data"));
            table.addCell(getNewColumn().add("Tipo"));
            table.addCell(getNewColumn().add("Valor"));

            for (int i = 0; i < jtable.getRowCount(); i++) {
                Object date = jtable.getValueAt(i, dateColumn);
                TransactionType type = TransactionType.getByName(jtable.getValueAt(i, typeColumn).toString());
                Object value = jtable.getValueAt(i, valueColumn);

                table.addCell(getNewRow().add(date.toString()));
                table.addCell(getNewRow().add(type.toString()));
                table.addCell(getNewRow().add(type.getSignal() + value.toString()));
            }

            document.add(table);
            document.close();

            Popup.show("Extrato", "<html>PDF gerado com sucesso em:<br/>" + file.getPath() + "</html>", "OK", null);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Cell getNewColumn() throws IOException {
        PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER);

        Cell column = new Cell();

        column.setFont(font);
        column.setBold();

        return column;
    }

    private static Cell getNewRow() throws IOException {
        PdfFont font = PdfFontFactory.createFont(FontConstants.COURIER);

        Cell row = new Cell();

        row.setFont(font);

        return row;
    }
}
