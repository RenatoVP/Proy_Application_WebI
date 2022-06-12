package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cibertec.entity.Usuario;
import com.cibertec.repository.IUsuarioRepository;

/**
 * @author dael2
 *
 */
@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioRepository repou;

	@GetMapping("/login/cargar")
	public String cargarform(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "Usuario/Login";
	}

	@PostMapping("/login/validar")
	public String validarIngreso(@ModelAttribute Usuario usuario, Model model) {
		//System.out.println("Del Login: " + usuario); // correo y clave
		Usuario u = repou.findByCorreoAndClave(usuario.getCorreo(), usuario.getClave());
		//System.out.println("Del Find(Busqueda): " + u);
		if (u == null) {
			model.addAttribute("error", "Usuario o Clave Incorrecto");
			return "Login";
		}
		model.addAttribute("usuario", u);
		return "redirect:/Dashboard";
	}

	@GetMapping("/usuario/cargar")
	public String cargarformRegistro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "Usuario/Registro";
	}

	@PostMapping("/usuario/grabar")
	public String grabarRegistro(@ModelAttribute Usuario usuario, Model model) {
		System.out.println(usuario);
		try {
			repou.save(usuario);
			model.addAttribute("success", "Registro exitoso");
		} catch (Exception e) {
			model.addAttribute("error", "Error al regitrar " + e.getMessage());
		}
		return "Registro";
	}
}