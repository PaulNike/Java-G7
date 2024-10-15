package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

}
