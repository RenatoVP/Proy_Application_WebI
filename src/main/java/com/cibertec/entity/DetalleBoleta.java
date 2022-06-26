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
@Table(name = "boleta_has_producto")
@Data
public class DetalleBoleta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "idboleta")
	private int idboleta;
	
	@ManyToOne
	@JoinColumn(name = "idboleta", insertable = false, updatable = false)
	private Boleta boleta;
	
	@Column(name = "idproducto")
	private String idproducto;
	
	@ManyToOne
	@JoinColumn(name = "idproducto", insertable = false, updatable = false)
	private Producto producto;
	private int cant;
	private double total;
}
