package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RegimenFiscal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegimenFiscal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimenFiscalRepository extends JpaRepository<RegimenFiscal, Long> {}