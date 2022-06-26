package com.cibertec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "boleta")
@Data
public class Boleta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "idusuario")
	private int idusuario;
	
	@Column(name = "idcliente")
	private int idcliente;
	
	@Column(name = "fecha")
	private String fecha;
	
	@Column(name = "totalpago")
	private double totalpago;
}
