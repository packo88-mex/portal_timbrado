package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Estados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadosRepository extends JpaRepository<Estados, Long> {}
