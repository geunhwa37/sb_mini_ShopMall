package org.zerock.sb_mini.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.sb_mini.product.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


}
