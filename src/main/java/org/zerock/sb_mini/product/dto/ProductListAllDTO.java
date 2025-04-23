package org.zerock.sb_mini.product.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductListAllDTO {

    private Long pno;
    private String pname;
    private int price;
    private String seller;

    private List<String> fileNames;

    private double avgRating; //평점
    private long reviewCnt;

}
