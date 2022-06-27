package com.cibertec.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.entity.Cliente;


public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Query("select c from Cliente c where c.dni like %?1%")
	public List<Cliente> findAllBydni(String dni);
	

}
