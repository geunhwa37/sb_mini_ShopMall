package org.zerock.sb_mini.product;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb_mini.product.dto.PageRequestDTO;
import org.zerock.sb_mini.product.dto.PageResponseDTO;
import org.zerock.sb_mini.product.dto.ProductListDTO;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.repository.ProductRepository;

import java.util.Arrays;

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

            //상품하나당 이미지 2개
            product.addImage(i +"_img0.jpg");
            product.addImage(i +"_img1.jpg");

            repo.save(product);
        }//end for
    }

    @Test
    public void listProducts() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = repo.list(pageable);

        result.forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void listQuerydsl() {

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = repo.listQuerydsl(requestDTO);

        log.info(result);
    }



}
