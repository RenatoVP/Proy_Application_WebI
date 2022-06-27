package com.cibertec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@Column(name = "apellidos")
	private String apellido;
	@Column(name = "username")
	private String correo;
	@Column(name = "contrasena")
	private String clave;
	@Column(name = "fecnac")
	private String fechaNacimiento;
	@Column(name = "idrol")
	private int idrol;
	@ManyToOne
	@JoinColumn(name = "idrol", insertable = false, updatable = false)
	private Rol rol;
	@Column(name = "idestado")
	private int idestado;
	@ManyToOne
	@JoinColumn(name = "idestado", insertable = false, updatable = false)
	private Estado estado;
}
