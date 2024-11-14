package com.codigo.unit_testing.dao;

import com.codigo.unit_testing.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
    boolean existsByNumeroDocumento(String numeroDocumento);

    boolean existsByRazonSocial(String razonSocial);
}
