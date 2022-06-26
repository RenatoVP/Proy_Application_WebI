package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.entity.Boleta;

@Repository
public interface IBoletaRepository extends JpaRepository<Boleta, Integer> {
	
}
