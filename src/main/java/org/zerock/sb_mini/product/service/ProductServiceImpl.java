package org.zerock.sb_mini.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.sb_mini.product.dto.*;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.repository.ProductRepository;
import org.zerock.sb_mini.util.FileUploadUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final FileUploadUtil fileUploadUtil;

    @Override
    //등록
    public Long add(ProductAddDTO dto) {

        // 파일 업로드 처리
        List<String> uploadedFileNames = new ArrayList<>();
        try {
            // 파일 업로드
            uploadedFileNames = fileUploadUtil.uploadFiles(dto.getFiles());
        } catch (Exception e) {
            log.error("파일 업로드 실패: " + e.getMessage());
            // 예외 처리 (파일 업로드 실패 시)
        }

        ProductEntity productEntity = addDTOToEntity(dto);

        // 업로드된 파일명을 엔티티에 추가
        uploadedFileNames.forEach(fileName -> productEntity.addFile(fileName));

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
        productEntity.clearFiles();
        try {
            // 파일 업로드
            List<String> uploadedFileNames = fileUploadUtil.uploadFiles(dto.getFiles());
            // 업로드된 파일명을 엔티티에 추가
            uploadedFileNames.forEach(fileName -> productEntity.addFile(fileName));
        } catch (Exception e) {
            log.error("파일 업로드 실패: " + e.getMessage());
            // 예외 처리 (파일 업로드 실패 시)
        }

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
