package org.zerock.sb_mini.product;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.sb_mini.product.dto.PageRequestDTO;
import org.zerock.sb_mini.product.dto.PageResponseDTO;
import org.zerock.sb_mini.product.dto.ProductListDTO;
import org.zerock.sb_mini.product.dto.ProductReadDTO;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.repository.ProductRepository;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class ProductRepoTests {

    @Autowired(required = false)
    ProductRepository repo;

    //등록
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

    //목록
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

    //조회 - 1개
    @Test
    public void readProduct() {
        Optional<ProductEntity> result = repo.findById(31L);

        ProductEntity product = result.get();

        //tbl_product_img 테이블은 처리되지 않음
        log.info(product);
    }

    @Test
    public void read2(){

        ProductEntity product = repo.selectOne(31L);

        log.info(product); //tbl_product_img 테이블은 처리되지 않음
        log.info(product.getImages());
    }

    @Test
    //이미지까지 한번에 나오게
    public void read3(){

        //ElementCollection이 여러 개인 경우 한 개의 객체로 변환하는데 어려움이 있다.
        //Java 코드를 이용해서 변환해야만 한다.
        ProductEntity product = repo.selectOne(31L);

        ProductReadDTO readDTO = new ProductReadDTO(product);

        log.info(readDTO);
    }

    @Test
    @Commit
    public void updateProduct() {
        ProductEntity product = repo.selectOne(31L);

        product.changePrice(3000);

        repo.save(product);
    }

    @Test
    @Commit
    public void deleteProduct() {
        ProductEntity product = repo.selectOne(57L);

        product.softDelete();

        repo.save(product);
    }



}
