package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RepresentacionGrafica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RepresentacionGrafica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentacionGraficaRepository extends JpaRepository<RepresentacionGrafica, Long> {}
