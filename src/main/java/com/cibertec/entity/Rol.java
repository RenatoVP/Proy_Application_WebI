package com.cibertec.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "rol")
@Data
public class Rol {
	@Id
	private int id;
	private String descripcion;
}
