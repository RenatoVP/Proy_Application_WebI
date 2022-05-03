package com.cibertec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "producto")
@Data
public class Producto {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "precio")
	private double precio;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "idcategoria")
	private int idcategoria;
	
	@JoinColumn(name = "idcategoria", insertable = false, updatable = false)
	@ManyToOne
	private Categoria categoria;
	
	@Column(name = "idestado")
	private int idestado;
	
	@JoinColumn(name = "idestado", insertable = false, updatable = false)
	@ManyToOne
	private Estado estado;
}
