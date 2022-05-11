package com.example.demo.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class EntradaPdfExporter {
	
	private List<Entrada> listaEntradas;
    
    public EntradaPdfExporter(List<Entrada> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }
     
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(8);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("ID", font));
         
        table.addCell(cell);
        
        //cell.setPhrase(new Phrase("Porteiro", font));
        //table.addCell(cell);
         
        cell.setPhrase(new Phrase("Data e Hora de Entrada", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Veículo", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Placa", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Nome do Motorista", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Número do Apartamento", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Tipo da Entrada", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Status da Entrada", font));
        table.addCell(cell);
        
        //cell.setPhrase(new Phrase("Data e Hora de Saída", font));
        //table.addCell(cell);
    }
    
    private void writeTableData(PdfPTable table) {
        for (Entrada entrada : listaEntradas) {
            table.addCell(String.valueOf(entrada.getId()));
            //table.addCell(entrada.getNomeUsuario());
            table.addCell(entrada.converterData(entrada.getHoraEntrada()));
            table.addCell(entrada.getVeiculo());
            table.addCell(entrada.getPlaca());
            table.addCell(entrada.getNomeMotorista());
            table.addCell(entrada.getNumeroApt());
            table.addCell(entrada.getTipo().getName());
            table.addCell(entrada.getStatus().getName());
            //table.addCell(converterData(entrada.getHoraSaida()));
        }
    }
    
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("Relatório de Entradas e Saídas", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f,3.0f, 3.0f, 2.0f, 3.0f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}
