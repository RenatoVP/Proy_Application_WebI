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
	private int id; 
		
		
		private String  razon_social;
		
		
		private String dni;
		
		
		private String telefono;
		
		private String email ;
}
