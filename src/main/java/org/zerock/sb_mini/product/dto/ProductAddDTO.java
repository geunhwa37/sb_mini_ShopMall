package org.zerock.sb_mini.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductAddDTO {

    private String pname;

    private String pdesc;

    private int price;

    private List<String> imageNames;
}
