package com.cibertec.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.entity.Cliente;

import com.cibertec.repository.IClienteRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;




@Controller
public class ClienteController {
	
	@Autowired
	private IClienteRepository repocli;
	
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	@GetMapping("Cliente/Consulta")
	public String consultaClieForm(Model model) {
		return "Cliente/ConsultaCliente";
	}
	
	@PostMapping("Cliente/Consulta")
	public String consultaCliente(@RequestParam String dniCli, Model model) {
		List<Cliente> lstClientes;
		
			lstClientes = repocli.findAllBydni(dniCli);
		
		model.addAttribute("lstClientes", lstClientes);
		return "Cliente/ConsultaCliente";
	}
	
	@GetMapping("/Cliente/Cargar")
	public String cargarform(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "Cliente/CrudCliente";
	}
	
	@PostMapping("/Cliente/Grabar")
	public String grabarform(@ModelAttribute Cliente cliente ,Model model) {
		System.out.println(cliente);
		try {
		repocli.save(cliente);
		model.addAttribute("mensaje","Cliente Registrado");
		} catch (Exception e) {
			model.addAttribute("error","Error al registrar Cliente");
		}
		return "Cliente/CrudCliente";
		
	}
	
	
	@GetMapping("/Cliente/Listar")
	public String listadoClientes(Model model,@Param("String palabraClave") String palabraClave) {
		model.addAttribute("lstClientes",repocli.findAll());
		model.addAttribute("palabraClave",palabraClave);
		return "Cliente/ListarCliente";
		
	}

	@PostMapping("/Cliente/Editar")
	public String busPro(@ModelAttribute Cliente p,Model model) {
		System.out.println(p);
	
		model.addAttribute("cliente",repocli.findById(p.getId()));
	
		return "Cliente/CrudCliente";
		
	}
	
	@PostMapping("/Cliente/Eliminar")
	public String elimina(@ModelAttribute Cliente cliente, Model model) {
	repocli.delete(cliente);
		return "redirect:/Cliente/Listar";
	}
	
	
	@GetMapping("/Cliente/Reporte")
	public void grafico(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		
		try {
			String ru = resourceLoader.getResource("classpath:ClienteReport.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, datasource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
