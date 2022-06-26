package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.entity.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, String>{
	@Query("select p from Producto p where p.descripcion like %?1%")
	public List<Producto> findAllByProductName(String productName);
	
	@Query("select p from Producto p where p.descripcion like %?1% and p.idcategoria = ?2")
	public List<Producto> findAllByProductNameAndCategory(String productName, int idCategoria);
}
