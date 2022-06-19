package com.cibertec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/Producto")
public class ProductoController {
	
	@Autowired
	private IProductoRepository ProductoRepo;
	
	@Autowired
	private ICategoriaRepository CategoriaRepo;
	
	@Autowired
	private IEstadoRepository EstadoRepo;
	
	@GetMapping("/Listar")
	public String listaProductos(Model model) {
		model.addAttribute("lstProducto", ProductoRepo.findAll());
		return "Producto/listaProductos";
	}
	
	@GetMapping("/Consulta")
	public String consultaProductoForm(Model model) {
		return "Producto/consultaProductos";
	}
	
	@PostMapping("/Consulta")
	public String consultaProducto(@RequestParam String nomProducto, Model model) {
		List<Producto> lstProductos;
		
		if(nomProducto.equals("")) {
			lstProductos = ProductoRepo.findAll();
		}else {
			lstProductos = ProductoRepo.findAllByProductName(nomProducto);
		}
		
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
}
