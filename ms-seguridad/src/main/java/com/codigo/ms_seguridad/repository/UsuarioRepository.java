package com.codigo.ms_seguridad.repository;

import com.codigo.ms_seguridad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
