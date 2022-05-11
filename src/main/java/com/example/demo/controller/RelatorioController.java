package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.model.Entrada;
import com.example.demo.service.GestaoEntradaService;

@Controller
public class RelatorioController {

	/*
	@Autowired
	private GestaoEntradaService entradaService;
	@Autowired 
	ServletContext context;

	@GetMapping(value = "/gerarPdf")
	public void gerarPdf(HttpServletRequest request, HttpServletResponse response) {

		List<Entrada> entradas = entradaService.listar();
		boolean isFlag = entradaService.gerarPdf(entradas, context, request, response);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"entradas"+".pdf");
			filedownload(fullPath, response, "entradas.pdf");
		}
	}

	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if(file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader ("content-disposition", "attachment; filename="+fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer= new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = inputStream.read(buffer)) != -1) { 
					outputStream.write(buffer, 0, bytesRead); 
					} 
				inputStream.close();
				outputStream.close();
				file.delete();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} */
}
