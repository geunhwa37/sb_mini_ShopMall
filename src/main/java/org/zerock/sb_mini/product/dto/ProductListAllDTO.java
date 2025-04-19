package org.zerock.sb_mini.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductListAllDTO {

    private Long pno;
    private String pname;
    private int price;
    private String seller;
    private List<String> imgNames;

    private double avgRating; //평점
    private long reviewCnt;

}
