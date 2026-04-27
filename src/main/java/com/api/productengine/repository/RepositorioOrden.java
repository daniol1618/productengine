
package com.api.productengine.repository;

import com.api.productengine.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioOrden extends JpaRepository<Orden, Long> {
}
