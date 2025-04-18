package org.zerock.sb_mini.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb_mini.product.dto.*;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    //등록
    public Long add(ProductAddDTO dto) {

        ProductEntity productEntity = addDTOToEntity(dto);

        repository.save(productEntity);

        return productEntity.getPno();
    }

    @Override
    public ProductReadDTO read(Long id) {

        return new ProductReadDTO(repository.selectOne(id));
    }

    @Override
    @Transactional(readOnly = true)//복잡한 조회 작업은 성능을 위해
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        return repository.listQuerydsl(pageRequestDTO);
    }

    @Override
    public void modify(ProductModifyDTO dto){

        //상품 엔티티 조회한 후에
        ProductEntity productEntity = repository.selectOne(dto.getPno());
        //변경 내용을 반영하고
        productEntity.changePname(dto.getPname());
        productEntity.changePrice(dto.getPrice());
        productEntity.changeDesc(dto.getPdesc());
        //이미지 조정하고
        productEntity.clearImages();
        dto.getImageNames().forEach(imgName -> productEntity.addImage(imgName));

        //변경감지 혹은 save - (명시적 저장이 필요한 경우나 새로운 엔티티 추가할 때는 save()를 사용)
        repository.save(productEntity);

    }

    @Override
    public void remove(long pno) {
        ProductEntity productEntity = repository.selectOne(pno);

        productEntity.softDelete();

        repository.save(productEntity);
    }


}
