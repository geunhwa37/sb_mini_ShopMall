package org.zerock.sb_mini.product.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.entities.ProductImgEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductReadDTO {

    private Long pno;

    private String pname;
    private int price;
    private String pdesc;

    private List<String> files = new ArrayList<>();  // MultipartFile 대신 URL
    private List<String> uploadedFileNames; // 원본 파일명 리스트

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    public ProductReadDTO(ProductEntity entity) {
        this.pno = entity.getPno();
        this.pname = entity.getPname();
        this.price = entity.getPrice();
        this.pdesc = entity.getPdesc();

        // URL 경로 포함된 파일 리스트
        this.files = entity.getFiles()
                .stream()
                .map(img -> getFileUrl(img.getFileName()))  // getFileUrl 메서드를 사용하여 URL로 변환
                .collect(Collectors.toList());
        // 원본 파일명 리스트
        this.uploadedFileNames = entity.getFiles()
                .stream()
                .map(img -> img.getFileName())
                .collect(Collectors.toList());

        this.regDate = entity.getRegDate();
        this.modDate = entity.getModDate();
    }

    // 파일 URL을 반환
    public String getFileUrl(String fileName) {
        return "http://localhost:8080/view/" + fileName; // 파일 URL 경로 반환
    }

}

