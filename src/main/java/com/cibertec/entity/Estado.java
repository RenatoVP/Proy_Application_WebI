package com.cibertec.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "estado")
@Data
public class Estado {
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "descripcion")
	private String descripcion;
}
