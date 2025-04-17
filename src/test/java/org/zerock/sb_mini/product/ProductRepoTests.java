package org.zerock.sb_mini.product;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.repository.ProductRepository;

@SpringBootTest
@Log4j2
public class ProductRepoTests {

    @Autowired(required = false)
    ProductRepository repo;

    @Test
    public void insertProduct() {

        for(int i = 0; i < 30; i++){
            ProductEntity product = ProductEntity.builder()
                    .pname("Product" + i)
                    .price(5000)
                    .seller("user00")
                    .build();

            repo.save(product);
        }//end for
    }


}
