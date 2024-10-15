package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity,Long> {

}
