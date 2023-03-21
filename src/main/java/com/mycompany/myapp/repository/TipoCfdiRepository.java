package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TipoCfdi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoCfdi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoCfdiRepository extends JpaRepository<TipoCfdi, Long> {}
