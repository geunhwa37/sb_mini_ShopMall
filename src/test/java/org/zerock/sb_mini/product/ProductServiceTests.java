package org.zerock.sb_mini.product;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb_mini.product.dto.*;
import org.zerock.sb_mini.product.service.ProductService;

import java.util.List;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Test
    public void testAddProduct() {
        ProductAddDTO dto = new ProductAddDTO();
        dto.setPname("Test");
        dto.setPdesc("Test");
        dto.setPrice(1000);
        dto.setImageNames(List.of("aaa.jpg","bbb.jpg","ccc.jpg"));

        Long pno = service.add(dto);

        log.info(pno);
    }

    @Test
    public void testReadProduct() {

        ProductReadDTO dto = service.read(1L);
        log.info(dto);
    }

    @Test
    public void testListProduct() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> dto = service.list(pageRequestDTO);

        log.info(dto);
    }

    @Test
    public void testModifyProduct() {
        ProductModifyDTO dto = new ProductModifyDTO();
        dto.setPno(2L);
        dto.setPdesc("Test");
        dto.setPrice(1000);
        dto.setImageNames(List.of("aaa.jpg","bbb.jpg","ccc.jpg"));
        service.modify(dto);

        log.info(service.read(2L));
    }

    @Test
    public void testRemoveProduct() {
        service.remove(3L);
    }
}
