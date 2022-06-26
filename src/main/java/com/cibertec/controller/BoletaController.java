package com.cibertec.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.entity.Boleta;
import com.cibertec.entity.BoletaProducto;
import com.cibertec.entity.DetalleBoleta;
import com.cibertec.entity.Producto;
import com.cibertec.repository.IBoletaRepository;
import com.cibertec.repository.IDetalleBoletaRepository;
import com.cibertec.repository.IProductoRepository;
import com.cibertec.repository.IUsuarioRepository;

@Controller
@RequestMapping("/boleta")
public class BoletaController {
	
	@Autowired
	private IBoletaRepository boletaRepo;
	
	@Autowired
	private IProductoRepository productoRepo;
	
	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	@Autowired
	private IDetalleBoletaRepository dbRepo;
	
	//Utilidades
	private ArrayList<BoletaProducto> lstProductos = new ArrayList<BoletaProducto>();
	
	double totalAPagar = 0.0;
	int item = 0;
	double base = 0.0;
	
	// Carga la pagina de formularioboleta
	@GetMapping("/Registrar")
	public String registraBoletaForm(Model model) {
		model.addAttribute("boleta", new Boleta());
		model.addAttribute("lstUsuario", usuarioRepo.findAll());
		model.addAttribute("totalAPagar", totalAPagar);
		model.addAttribute("lstProducto", lstProductos);
		
		return "Boleta/formBoleta";
	}
	
	// Almacena los datos de la boleta con sus respectivos productos solicitados
	// en la base de datos
	@PostMapping("/Registrar")
	public String registraBoleta(@ModelAttribute(name = "boleta") Boleta boleta, Model model) {
		try {
			//Guardar Boleta
			boleta.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			boleta.setTotalpago(totalAPagar);
			
			boletaRepo.save(boleta);
			System.out.println(boleta.getId());
			
			//Guardar Detalle Boleta
			for(int i = 0; i <= lstProductos.size() - 1; i++) {
				DetalleBoleta db = new DetalleBoleta();
				db.setIdboleta(boleta.getId());
				db.setIdproducto(lstProductos.get(i).getIdproducto());
				db.setCant(lstProductos.get(i).getCantidad());
				db.setTotal(lstProductos.get(i).getSubtotal() * lstProductos.get(i).getCantidad());
				
				dbRepo.save(db);
			}
			
			totalAPagar = 0.0;
			lstProductos.clear();
			item = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		boletaRepo.save(boleta);
		return "redirect:/boleta/Registrar";
	}
	
	// Se ejecuta al agregar un producto en la boleta
	@PostMapping("/productoBoleta")
	public String productoBoleta(@ModelAttribute Producto producto, Model model) {
		try {
			item++;
			Producto productoEncontrado = productoRepo.getById(producto.getId());
			// Almacena el producto encontrado
			BoletaProducto bp = new BoletaProducto();
			bp.setItem(item);
			bp.setIdproducto(productoEncontrado.getId());
			bp.setProducto(productoEncontrado.getDescripcion());
			bp.setPrecio(productoEncontrado.getPrecio());
			bp.setCategoria(productoEncontrado.getCategoria().getDescripcion());
			bp.setCantidad(1);
			bp.setSubtotal(productoEncontrado.getPrecio() * 1);
			
			lstProductos.add(bp);
			
			//Agrega el importe a pagar
			totalAPagar += bp.getSubtotal() * bp.getCantidad();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/boleta/Registrar";
	}
	
	@PostMapping("/actualizaStock")
	public String actualizaStock(@RequestParam int index, @RequestParam int cantidad, Model model) {
		//Obtenemos precio inicial antes de actualizarlo
		base = lstProductos.get(index - 1).getSubtotal() * lstProductos.get(index - 1).getCantidad();
		
		//Actualizamos el stock del producto seleccionado
		lstProductos.get(index - 1).setCantidad(cantidad);
		
		//Subtotal actualizado
		double precioActualizado = lstProductos.get(index - 1).getSubtotal() * lstProductos.get(index - 1).getCantidad();
		
		totalAPagar += precioActualizado - base;
		
		return "redirect:/boleta/Registrar";
	}
	
	// Se ejecuta al quitar un producto seleccionado por error o por cambio de
	// decision por parte del cliente
	@PostMapping("/quitaProducto")
	public String quitaProducto(@RequestParam int index, Model model) {
		//Restamos el precio total del producto seleccionado mas su cantidad
		totalAPagar -= lstProductos.get(index - 1).getSubtotal() * lstProductos.get(index - 1).getCantidad();
		item--;
		lstProductos.remove(index -1);
		
		return "redirect:/boleta/Registrar";
	}
}
