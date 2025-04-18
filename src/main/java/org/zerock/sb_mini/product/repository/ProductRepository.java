package org.zerock.sb_mini.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb_mini.product.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductSearch {


    //목록 출력
    @Query("select p.pno, p.pname, p.price, p.seller, pi.imgName " +
            "from ProductEntity p left join p.images pi " +
            "where pi.ord = 0")
    Page<Object[]> list(Pageable pageable);

}
