package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.entity.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Integer> {

}
