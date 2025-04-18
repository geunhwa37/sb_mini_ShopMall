package org.zerock.sb_mini.product.dto;

import lombok.Data;
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

    private List<String> imageNames;

    public ProductReadDTO(ProductEntity entity) {
        this.pno = entity.getPno();
        this.pname = entity.getPname();
        this.price = entity.getPrice();
        this.pdesc = entity.getPdesc();
        this.imageNames = entity.getImages().stream().map(pi ->pi.getImgName()).collect(Collectors.toList());
    }
}

