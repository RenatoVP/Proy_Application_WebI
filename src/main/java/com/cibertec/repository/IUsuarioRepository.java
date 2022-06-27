package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByCorreoAndClave(String correo, String clave);
	
	List<Usuario> findAllByIdrol(int idrol);
	
	@Query("select u from Usuario u where u.nombre like %?1%")
	public List<Usuario> findAllByUserName(String usuname);
}
