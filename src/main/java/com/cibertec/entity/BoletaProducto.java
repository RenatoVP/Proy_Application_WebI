package com.cibertec.entity;

import lombok.Data;

@Data
public class BoletaProducto {
	private int item;
	private String idproducto;
	private String producto;
	private double precio;
	private String categoria;
	private int cantidad;
	private double subtotal;
}
