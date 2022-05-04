package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.NegocioException;
import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;
import com.example.demo.repository.EntradaRepository;
import com.example.demo.user.ControllerUsuario;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
@Transactional
public class GestaoEntradaService {

	@Autowired
	private EntradaRepository entradaRepository;

	public void finalizar(Long id) {
		Entrada entrada = buscar(id);
		if (entrada.getStatus().equals(StatusEntrada.FINALIZADA)) {
			throw new NegocioException("Entrada já finalizada!");
		} else {
			entrada.setHoraSaida(LocalDateTime.now());
			entrada.setStatus(StatusEntrada.FINALIZADA);
			;
			entradaRepository.save(entrada);
		}
	}

	public Entrada buscar(Long id) {
		return entradaRepository.findById(id).get();
	}

	public List<Entrada> buscarPlaca(String placa) {
		return (List<Entrada>) entradaRepository.findEntradaByPlaca(placa);
	}

	public String usuarioLogado() {
		ControllerUsuario uc = new ControllerUsuario();
		String nome = uc.getUsuario().getNomeUsuario();
		return nome;
	}

	public Entrada criar(Entrada entrada) {
		caracteres(entrada);
		entrada.setStatus(StatusEntrada.ABERTA);
		entrada.setHoraEntrada(LocalDateTime.now());
		return entradaRepository.save(entrada);
	}

	public List<Entrada> listar() {
		return (List<Entrada>) entradaRepository.findAll();
	}

	public List<Entrada> listarPorStatus(StatusEntrada status) {
		return (List<Entrada>) entradaRepository.findEntradaByStatus(status);
	}

	public String converterData(LocalDateTime data) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataFormatada = dateFormat.format(data);
		return dataFormatada;
	}

	public boolean gerarPdf(List<Entrada> entradas, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 15, 15, 45, 30);
		try {
			String filePath = context.getRealPath("/resources/reports");
			File file = new File(filePath);
			boolean exists = new File(filePath).exists();
			if (!exists) {
				new File(filePath).mkdirs();
			}
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "entradas" + ".pdf"));
			document.open();

			Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Paragraph paragraph = new Paragraph("Relatório de Entradas e Saídas", mainFont);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setIndentationLeft(50);
			paragraph.setIndentationRight(50);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);

			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10);

			Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
			Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

			float[] columnWidths = { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f };
			table.setWidths(columnWidths);

			PdfPCell porteiro = new PdfPCell(new Paragraph("Porteiro", tableHeader));
			porteiro.setBorderColor(BaseColor.BLACK);
			porteiro.setPaddingLeft(10);
			porteiro.setHorizontalAlignment(Element.ALIGN_CENTER);
			porteiro.setVerticalAlignment(Element.ALIGN_CENTER);
			porteiro.setBackgroundColor(BaseColor.GRAY);
			porteiro.setExtraParagraphSpace(5f);
			table.addCell(porteiro);

			PdfPCell horaEntrada = new PdfPCell(new Paragraph("Data e Hora de Entrada", tableHeader));
			horaEntrada.setBorderColor(BaseColor.BLACK);
			horaEntrada.setPaddingLeft(10);
			horaEntrada.setHorizontalAlignment(Element.ALIGN_CENTER);
			horaEntrada.setVerticalAlignment(Element.ALIGN_CENTER);
			horaEntrada.setBackgroundColor(BaseColor.GRAY);
			horaEntrada.setExtraParagraphSpace(5f);
			table.addCell(horaEntrada);

			PdfPCell tipo = new PdfPCell(new Paragraph("Tipo da Entrada", tableHeader));
			tipo.setBorderColor(BaseColor.BLACK);
			tipo.setPaddingLeft(10);
			tipo.setHorizontalAlignment(Element.ALIGN_CENTER);
			tipo.setVerticalAlignment(Element.ALIGN_CENTER);
			tipo.setBackgroundColor(BaseColor.GRAY);
			tipo.setExtraParagraphSpace(5f);
			table.addCell(tipo);

			PdfPCell veiculo = new PdfPCell(new Paragraph("Veículo", tableHeader));
			veiculo.setBorderColor(BaseColor.BLACK);
			veiculo.setPaddingLeft(10);
			veiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
			veiculo.setVerticalAlignment(Element.ALIGN_CENTER);
			veiculo.setBackgroundColor(BaseColor.GRAY);
			veiculo.setExtraParagraphSpace(5f);
			table.addCell(veiculo);

			PdfPCell placa = new PdfPCell(new Paragraph("Placa", tableHeader));
			placa.setBorderColor(BaseColor.BLACK);
			placa.setPaddingLeft(10);
			placa.setHorizontalAlignment(Element.ALIGN_CENTER);
			placa.setVerticalAlignment(Element.ALIGN_CENTER);
			placa.setBackgroundColor(BaseColor.GRAY);
			placa.setExtraParagraphSpace(5f);
			table.addCell(placa);

			PdfPCell nomeMotorista = new PdfPCell(new Paragraph("Nome do Motorista", tableHeader));
			nomeMotorista.setBorderColor(BaseColor.BLACK);
			nomeMotorista.setPaddingLeft(10);
			nomeMotorista.setHorizontalAlignment(Element.ALIGN_CENTER);
			nomeMotorista.setVerticalAlignment(Element.ALIGN_CENTER);
			nomeMotorista.setBackgroundColor(BaseColor.GRAY);
			nomeMotorista.setExtraParagraphSpace(5f);
			table.addCell(nomeMotorista);

			PdfPCell numeroApt = new PdfPCell(new Paragraph("Número do Apartamento", tableHeader));
			numeroApt.setBorderColor(BaseColor.BLACK);
			numeroApt.setPaddingLeft(10);
			numeroApt.setHorizontalAlignment(Element.ALIGN_CENTER);
			numeroApt.setVerticalAlignment(Element.ALIGN_CENTER);
			numeroApt.setBackgroundColor(BaseColor.GRAY);
			numeroApt.setExtraParagraphSpace(5f);
			table.addCell(numeroApt);

			PdfPCell status = new PdfPCell(new Paragraph("Status da Entrada", tableHeader));
			status.setBorderColor(BaseColor.BLACK);
			status.setPaddingLeft(10);
			status.setHorizontalAlignment(Element.ALIGN_CENTER);
			status.setVerticalAlignment(Element.ALIGN_CENTER);
			status.setBackgroundColor(BaseColor.GRAY);
			status.setExtraParagraphSpace(5f);
			table.addCell(status);

			PdfPCell horaSaida = new PdfPCell(new Paragraph("Data e Hora de Saída", tableHeader));
			horaSaida.setBorderColor(BaseColor.BLACK);
			horaSaida.setPaddingLeft(10);
			horaSaida.setHorizontalAlignment(Element.ALIGN_CENTER);
			horaSaida.setVerticalAlignment(Element.ALIGN_CENTER);
			horaSaida.setBackgroundColor(BaseColor.GRAY);
			horaSaida.setExtraParagraphSpace(5f);
			table.addCell(horaSaida);

			for (Entrada entrada : entradas) {

				PdfPCell porteiroValue = new PdfPCell(new Paragraph(entrada.getNomeUsuario(), tableBody));
				porteiroValue.setBorderColor(BaseColor.BLACK);
				porteiroValue.setPaddingLeft(10);
				porteiroValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				porteiroValue.setVerticalAlignment(Element.ALIGN_CENTER);
				porteiroValue.setBackgroundColor(BaseColor.WHITE);
				porteiroValue.setExtraParagraphSpace(5f);
				table.addCell(porteiroValue);

				PdfPCell horaEntradaValue = new PdfPCell(new Paragraph(converterData(entrada.getHoraEntrada()), tableBody));
				horaEntradaValue.setBorderColor(BaseColor.BLACK);
				horaEntradaValue.setPaddingLeft(10);
				horaEntradaValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				horaEntradaValue.setVerticalAlignment(Element.ALIGN_CENTER);
				horaEntradaValue.setBackgroundColor(BaseColor.WHITE);
				horaEntradaValue.setExtraParagraphSpace(5f);
				table.addCell(horaEntradaValue);

				PdfPCell tipoValue = new PdfPCell(new Paragraph(entrada.getTipo().getName(), tableBody));
				tipoValue.setBorderColor(BaseColor.BLACK);
				tipoValue.setPaddingLeft(10);
				tipoValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				tipoValue.setVerticalAlignment(Element.ALIGN_CENTER);
				tipoValue.setBackgroundColor(BaseColor.WHITE);
				tipoValue.setExtraParagraphSpace(5f);
				table.addCell(tipoValue);

				PdfPCell veiculoValue = new PdfPCell(new Paragraph(entrada.getVeiculo(), tableBody));
				veiculoValue.setBorderColor(BaseColor.BLACK);
				veiculoValue.setPaddingLeft(10);
				veiculoValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				veiculoValue.setVerticalAlignment(Element.ALIGN_CENTER);
				veiculoValue.setBackgroundColor(BaseColor.WHITE);
				veiculoValue.setExtraParagraphSpace(5f);
				table.addCell(veiculoValue);

				PdfPCell placaValue = new PdfPCell(new Paragraph(entrada.getPlaca(), tableBody));
				placaValue.setBorderColor(BaseColor.BLACK);
				placaValue.setPaddingLeft(10);
				placaValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				placaValue.setVerticalAlignment(Element.ALIGN_CENTER);
				placaValue.setBackgroundColor(BaseColor.WHITE);
				placaValue.setExtraParagraphSpace(5f);
				table.addCell(placaValue);

				PdfPCell nomeMotoristaValue = new PdfPCell(new Paragraph(entrada.getNomeMotorista(), tableBody));
				nomeMotoristaValue.setBorderColor(BaseColor.BLACK);
				nomeMotoristaValue.setPaddingLeft(10);
				nomeMotoristaValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				nomeMotoristaValue.setVerticalAlignment(Element.ALIGN_CENTER);
				nomeMotoristaValue.setBackgroundColor(BaseColor.WHITE);
				nomeMotoristaValue.setExtraParagraphSpace(5f);
				table.addCell(nomeMotoristaValue);

				PdfPCell numeroAptValue = new PdfPCell(new Paragraph(entrada.getNumeroApt(), tableBody));
				numeroAptValue.setBorderColor(BaseColor.BLACK);
				numeroAptValue.setPaddingLeft(10);
				numeroAptValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				numeroAptValue.setVerticalAlignment(Element.ALIGN_CENTER);
				numeroAptValue.setBackgroundColor(BaseColor.WHITE);
				numeroAptValue.setExtraParagraphSpace(5f);
				table.addCell(numeroAptValue);

				PdfPCell statusValue = new PdfPCell(new Paragraph(entrada.getStatus().name(), tableBody));
				statusValue.setBorderColor(BaseColor.BLACK);
				statusValue.setPaddingLeft(10);
				statusValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				statusValue.setVerticalAlignment(Element.ALIGN_CENTER);
				statusValue.setBackgroundColor(BaseColor.WHITE);
				statusValue.setExtraParagraphSpace(5f);
				table.addCell(statusValue);

				PdfPCell horaSaidaValue = new PdfPCell(new Paragraph(converterData(entrada.getHoraSaida()), tableBody));
				horaSaidaValue.setBorderColor(BaseColor.BLACK);
				horaSaidaValue.setPaddingLeft(10);
				horaSaidaValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				horaSaidaValue.setVerticalAlignment(Element.ALIGN_CENTER);
				horaSaidaValue.setBackgroundColor(BaseColor.WHITE);
				horaSaidaValue.setExtraParagraphSpace(5f);
				table.addCell(horaSaidaValue);

			}

			document.add(table);
			document.close();
			writer.close();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	// return (List<Entrada>) entradaRepository.findById(id).get().getStatus() ==
	// StatusEntrada.ABERTA;
	public void caracteres(Entrada entrada) {
		// Convertendo a String da placa para letras Maiúsculas
		entrada.setPlaca(entrada.getPlaca().toUpperCase());

		char[] palavras = entrada.getVeiculo().toCharArray();

		for (int i = 1; i < palavras.length; i++) {
			// Convertendo todas as letras para minúsculo;
			if (Character.isAlphabetic(palavras[i])) {
				palavras[i] = Character.toLowerCase(palavras[i]);
			}
			// Se o caracter anterior for espaço, então o atual será maiúsculo;
			if (Character.isWhitespace(palavras[i - 1])) {
				palavras[i] = Character.toUpperCase(palavras[i]);
			}
		}
		// A primeira letra de toda frase ou palavra será maiúscula;
		palavras[0] = Character.toUpperCase(palavras[0]);
		// Retorna o Array de char como String;
		String nomeConvertido = new String(palavras);

		entrada.setVeiculo(nomeConvertido);

		// Fazendo a conversão do nome do motorista;
		char[] caractere = entrada.getNomeMotorista().toCharArray();

		for (int i = 1; i < caractere.length; i++) {
			if (Character.isAlphabetic(caractere[i])) {
				caractere[i] = Character.toLowerCase(caractere[i]);
			}
			if (Character.isWhitespace(caractere[i - 1])) {
				caractere[i] = Character.toUpperCase(caractere[i]);
			}
		}
		caractere[0] = Character.toUpperCase(caractere[0]);
		String nome = new String(caractere);

		entrada.setNomeMotorista(nome);
	}
}
