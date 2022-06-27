package com.cibertec.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="cliente")
@Data
public class Cliente {
	
	@Id
	private int idCliente; 
		
		
		private String nombreCliente;
		
		
		private String apellidoCliente;
		
		
		private String dniCliente;
}
