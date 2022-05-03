package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/Actualizar")
	public String actualizaProductoForm(Model model) {
		return "";
	}
	
	@PutMapping("/Actualizar")
	public String actualizaProducto(Model model) {
		return "";
	}
	
	@DeleteMapping("/Eliminar")
	public String eliminaProducto(Model model) {
		return "";
	}
}
