package org.zerock.sb_mini.product.service;

import org.zerock.sb_mini.product.dto.*;
import org.zerock.sb_mini.product.entities.ProductEntity;

import java.util.UUID;

public interface ProductService {

    Long add(ProductAddDTO productAddDTO);

    ProductReadDTO read(Long id);

    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    void modify(ProductModifyDTO dto);

    void remove(long pno);


    //addDTO -> Entity 변환
    //default는 자바에서 인터페이스 안에 구현이 있는 메서드를 정의할 때 씀.
    default ProductEntity addDTOToEntity(ProductAddDTO addDTO) {
        ProductEntity entity = ProductEntity.builder()
                .pname(addDTO.getPname())
                .pdesc(addDTO.getPdesc())
                .price(addDTO.getPrice())
                .build();

        return entity;
    }
}
