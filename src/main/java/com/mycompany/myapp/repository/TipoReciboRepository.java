package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoRecibo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoRecibo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoReciboRepository extends JpaRepository<TipoRecibo, Long> {}
