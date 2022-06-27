package com.cibertec.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.entity.Producto;
import com.cibertec.repository.ICategoriaRepository;
import com.cibertec.repository.IEstadoRepository;
import com.cibertec.repository.IProductoRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/Producto")
public class ProductoController {
	
	@Autowired
	private IProductoRepository ProductoRepo;
	
	@Autowired
	private ICategoriaRepository CategoriaRepo;
	
	@Autowired
	private IEstadoRepository EstadoRepo;
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping("/Listar")
	public String listaProductos(Model model) {
		model.addAttribute("lstProducto", ProductoRepo.findAll());
		return "Producto/listaProductos";
	}
	
	@GetMapping("/Consulta")
	public String consultaProductoForm(Model model) {
		model.addAttribute("lstCategoria", CategoriaRepo.findAll());
		return "Producto/consultaProductos";
	}
	
	@PostMapping("/Consulta")
	public String consultaProducto(@RequestParam String nomProducto, @RequestParam int idCategoria, Model model) {
		List<Producto> lstProductos;
		
		if(idCategoria == -1)
			lstProductos = ProductoRepo.findAllByProductName(nomProducto);
		else
			lstProductos = ProductoRepo.findAllByProductNameAndCategory(nomProducto, idCategoria);
		
		model.addAttribute("lstCategoria", CategoriaRepo.findAll());
		model.addAttribute("lstProducto", lstProductos);
		return "Producto/consultaProductos";
	}
	
	@GetMapping("/Registrar")
	public String registraProductoForm(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstCategoria", CategoriaRepo.findAll());
		model.addAttribute("lstEstado", EstadoRepo.findAll());
		return "Producto/registraProducto";
	}
	
	@PostMapping("/Registrar")
	public String registraProducto(@ModelAttribute(name = "producto") Producto producto, Model model) {
		ProductoRepo.save(producto);
		return "redirect:/Producto/Listar";
	}
	
	@PostMapping("/Editar")
	public String buscaProducto(@ModelAttribute Producto producto, Model model) {
		model.addAttribute("producto", ProductoRepo.findById(producto.getId()));
		model.addAttribute("lstCategoria", CategoriaRepo.findAll());
		model.addAttribute("lstEstado", EstadoRepo.findAll());
		return "Producto/registraProducto";
	}
	
	@PostMapping("/Elimina")
	public String elimina(@ModelAttribute Producto producto, Model model) {
		ProductoRepo.delete(producto);
		return "redirect:/Producto/Listar";
	}
	
	/*@GetMapping("/Reporte")
	public void reporte(@RequestParam int idCategoria, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"productosx-" + idCategoria + ".pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("pIdCategoria", idCategoria);
			
			String ru = resourceLoader.getResource("classpath:productosxCategoria.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, param, datasource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@GetMapping("/Grafico")
	public void grafico(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"grafico-productos.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		
		try {
			String ru = resourceLoader.getResource("classpath:graficoProductos.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, datasource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
