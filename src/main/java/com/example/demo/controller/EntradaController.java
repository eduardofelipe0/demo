package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.NegocioException;
import com.example.demo.model.Entrada;
import com.example.demo.model.EntradaPdfExporter;
import com.example.demo.model.StatusEntrada;
import com.example.demo.model.TipoEntrada;
import com.example.demo.repository.EntradaRepository;
import com.example.demo.service.GestaoEntradaService;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/entradas")
public class EntradaController {

	@Autowired
	private EntradaRepository entradaRepository;
	@Autowired
	private GestaoEntradaService gestaoEntradaService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView formulario() {
		ModelAndView modelAndView = new ModelAndView();
		Entrada entrada = new Entrada();
		modelAndView.setViewName("entrada/registro");
		modelAndView.addObject("entradaAtual", entrada);
		modelAndView.addObject("tipos", TipoEntrada.values());
		return modelAndView;
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public ModelAndView registrar(@Valid Entrada entrada, RedirectAttributes atributes, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			return new ModelAndView("layout", "conteudo", "/entradas/new");
		}
		gestaoEntradaService.criar(entrada);
		atributes.addFlashAttribute("message", "Entrada registrada com sucesso.");
		return new ModelAndView("redirect:/entradas/new");

	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView listarEntradas() {
		ModelMap model = new ModelMap();
		model.addAttribute("entradas", gestaoEntradaService.listar());
		model.addAttribute("conteudo", "entrada/listaEntradas");
		return new ModelAndView("layout", model);
	}

	@RequestMapping(value = "/export/pdf", method = RequestMethod.GET)
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Relatório de Entradas_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Entrada> listaEntradas = gestaoEntradaService.listAll();

		EntradaPdfExporter exporter = new EntradaPdfExporter(listaEntradas);
		exporter.export(response);

	}

	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public ModelAndView buscar(@RequestParam(value = "buscarEntrada", required = false) String buscarEntrada) {
		ModelMap model = new ModelMap();
		List<Entrada> entradas = gestaoEntradaService.buscarPlaca(buscarEntrada);
		model.addAttribute("entradas", entradas);
		model.addAttribute("conteudo", "entrada/listaEntradas");
		return new ModelAndView("layout", model);
		// @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
		// th:text="${#temporals.format(entrada.horaSaida, 'dd/MM/yyyy HH:mm')}"
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView buscarData(
			@RequestParam(value = "buscarData", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate buscarData) {
		ModelMap model = new ModelMap();
		List<Entrada> entradas = gestaoEntradaService.buscarData(buscarData);
		model.addAttribute("entradas", entradas);
		model.addAttribute("conteudo", "entrada/listaEntradas");
		return new ModelAndView("layout", model);
	}

	@RequestMapping(value = "/intervalo", method = RequestMethod.GET)
	public ModelAndView intervaloDatas(
			@RequestParam(value = "intervaloDatas", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime buscarData1,
			LocalDateTime buscarData2) {
		ModelMap model = new ModelMap();
		List<Entrada> entradas = gestaoEntradaService.intervaloDatas(buscarData1, buscarData2);
		model.addAttribute("entradas", entradas);
		model.addAttribute("conteudo", "entrada/listaEntradas");
		return new ModelAndView("layout", model);
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public ModelAndView listarPorStatus(@RequestParam(value = "statusEntrada", required = false) StatusEntrada status) {
		ModelMap model = new ModelMap();
		List<Entrada> entradas = gestaoEntradaService.listarPorStatus(status);
		if (status == null) {
			return new ModelAndView("redirect:/entradas/listar");
		}
		model.addAttribute("entradas", entradas);
		model.addAttribute("conteudo", "entrada/listaEntradas");
		return new ModelAndView("layout", model);
	}

	@ModelAttribute("stattus")
	public StatusEntrada[] statusEntrada() {
		return StatusEntrada.values();
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id, RedirectAttributes atributes) {
		ModelAndView modelAndView = new ModelAndView();
		Entrada entrada = gestaoEntradaService.buscar(id);
		if (entrada.getStatus() == StatusEntrada.FINALIZADA) {
			throw new NegocioException("Esta entrada já foi finalizada, portanto não é mais possível editá-la.");
			// atributes.addFlashAttribute("message", "Esta entrada já foi finalizada,
			// portanto não é mais possível editá-la.");
		}
		modelAndView.setViewName("entrada/registro");
		modelAndView.addObject("entradaAtual", entrada);
		modelAndView.addObject("tipos", TipoEntrada.values());
		atributes.addFlashAttribute("message", "Entrada editada com suceso.");
		return modelAndView;
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.GET)
	public String deletar(@PathVariable("id") Long id, RedirectAttributes atributes) {
		Entrada entrada = entradaRepository.findById(id).get();
		entradaRepository.delete(entrada);
		atributes.addFlashAttribute("message", "Entrada removida com sucesso.");
		return "redirect:/entradas/status?statusEntrada=ABERTA";
		// return "redirect:/entradas/listar";
	}

	@RequestMapping(value = "/finalizar/{id}", method = RequestMethod.GET)
	public String finalizar(@PathVariable Long id, RedirectAttributes atributes) {
		gestaoEntradaService.finalizar(id);
		atributes.addFlashAttribute("message", "Saída registrada com sucesso.");
		return "redirect:/entradas/status?statusEntrada=ABERTA";
	}

}
