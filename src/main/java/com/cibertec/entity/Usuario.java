package com.cibertec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "username")
	private String correo;
	@Column(name = "contraseña")
	private String clave;
	@Column(name = "fecNac")
	private String fechaNacimiento;
	@Column(name = "idrol")
	private int idrol;
	@Column(name = "idestado")
	private int idestado;
}
