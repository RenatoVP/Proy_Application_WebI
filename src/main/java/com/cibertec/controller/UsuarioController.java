package com.cibertec.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.ResourceLoader;

import com.cibertec.entity.Usuario;
import com.cibertec.repository.IEstadoRepository;
import com.cibertec.repository.IRolRepository;
import com.cibertec.repository.IUsuarioRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	@Autowired
	private DataSource datasource;
	@Autowired
	private ResourceLoader resourceLoader;

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
	
	//Consulta
	@GetMapping("/usuario/consulta")
	public String consultaProductoForm(Model model) {
		model.addAttribute("lstusuario", repou.findAll());
		return "Usuario/consultaUsuario";
	}
	
	@PostMapping("/usuario/consulta")
	public String consultaProducto(@RequestParam String nomUsu, Model model) {
		List<Usuario> lstUsuarios;
		
		lstUsuarios = repou.findAllByUserName(nomUsu);
			
		model.addAttribute("lstusuario", lstUsuarios);
		return "Usuario/consultaUsuario";
	}
	
	//Editar
	@PostMapping("/usuario/editar")
	public String buscaUsuario(@ModelAttribute Usuario usuario, Model model) {
		model.addAttribute("usuario", repou.findById(usuario.getId()));
		model.addAttribute("lstrol", repor.findAll());
		model.addAttribute("lstestado", repoe.findAll());
		return "Usuario/Registro";
	}
	
	//Elimina
	@PostMapping("/usuario/elimina")
	public String elimina(@ModelAttribute Usuario usuario, Model model) {
		repou.delete(usuario);
		return "redirect:/usuario/lista";
	}
	
	//Reporte
	@GetMapping("/usuario/reporte")
	public void grafico(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"Reporte_Usuario.pdf\";");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/pdf");
		
		try {
			String ru = resourceLoader.getResource("classpath:reporteUsuarios.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, datasource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}