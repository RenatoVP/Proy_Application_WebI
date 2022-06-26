package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.entity.Usuario;
import com.cibertec.repository.IEstadoRepository;
import com.cibertec.repository.IRolRepository;
import com.cibertec.repository.IUsuarioRepository;

/**
 * @author dael2
 *
 */
@Controller
public class UsuarioController {
	@Autowired
	private IUsuarioRepository repou;
	@Autowired
	private IEstadoRepository repoe;
	@Autowired
	private IRolRepository repor;

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
			return "Usuario/Login";
		}
		model.addAttribute("usuario", u);
		return "redirect:/Dashboard";
	}
	
	//Listar
	@GetMapping("/usuario/lista")
	public String listar(Model model) {
		model.addAttribute("lstusuario", repou.findAll());
		return "Usuario/listaUsuario";
	}
	
	//Registrar
	@GetMapping("/usuario/cargar")
	public String cargarformRegistro(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("lstrol", repor.findAll());
		model.addAttribute("lstestado", repoe.findAll());
		return "Usuario/Registro";
	}

	@PostMapping("/usuario/grabar")
	public String grabarRegistro(@ModelAttribute Usuario usuario, RedirectAttributes redirect) {
		try {
			repou.save(usuario);
			redirect.addFlashAttribute("success", "Registro exitoso");
		} catch (Exception e) {
			redirect.addFlashAttribute("error", "Error al regitrar " + e.getMessage());
		}
		return "redirect:/usuario/lista";
	}
}