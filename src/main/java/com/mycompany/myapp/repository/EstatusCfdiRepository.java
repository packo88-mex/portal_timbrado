package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EstatusCfdi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstatusCfdi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusCfdiRepository extends JpaRepository<EstatusCfdi, Long> {}
