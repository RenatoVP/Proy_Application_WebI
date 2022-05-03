package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.entity.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, String>{

}
