package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.entity.Comprobante;

@Controller
public class ComprobanteController {

	@GetMapping("comprobante/cargar")
	public String cargarform(Model model) {
		model.addAttribute("comrobante", new Comprobante());
		//lst -> cboCategorias
		// lst -> cboProveedores
		return "RegistrarComprobante";
	}
	@PostMapping("comprobante/grabar")
	public String grabarform(@ModelAttribute Comprobante comprobante) {
		System.out.println(comprobante);
		return "RegistrarComprobante";
	}

}
