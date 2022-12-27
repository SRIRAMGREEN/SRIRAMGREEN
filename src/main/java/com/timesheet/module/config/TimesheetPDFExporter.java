package com.timesheet.module.config;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.timesheet.module.timesheet.entity.Timesheet;


public class TimesheetPDFExporter {
    private List<Timesheet> listTimesheet;

    public TimesheetPDFExporter(List<Timesheet> listTimesheet) {
        this.listTimesheet = listTimesheet;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.ORANGE);

        cell.setPhrase(new Phrase("Employee ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("total Hours", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("timesheet StartDate", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("timesheet EndDate", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Employee", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("timesheet Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("task", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("project Manager", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Timesheet timesheet : listTimesheet) {
            table.addCell(String.valueOf(timesheet.getEmployee().getId()));
            table.addCell((timesheet.getTotalHours()).toString());
            table.addCell((timesheet.getTimesheetStartDate()));
            table.addCell((timesheet.getTimesheetEndDate()));
            table.addCell((timesheet.getEmployee().getEmployeesName()));
            table.addCell((timesheet.getTimesheetStatus()));
            table.addCell((timesheet.getTask().getTaskName()));
            table.addCell((timesheet.getProjectManager().getProjectManagerName()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph paragraph = new Paragraph("List of Timesheet", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(15 );
        table.setWidths(new float[] {3.0f, 2.7f, 3.5f, 3.5f, 3.0f,3.0f,2.7f,3.5f });


        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}


