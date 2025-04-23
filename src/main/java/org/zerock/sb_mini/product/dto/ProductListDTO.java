package org.zerock.sb_mini.product.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.sb_mini.product.entities.ProductImgEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

    private Long pno;
    private String pname;
    private int price;
    private String seller;

    private String fileName;

    private double avgRating; //평점
    private long reviewCnt;


}


