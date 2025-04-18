package org.zerock.sb_mini.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductModifyDTO {

    private long pno;

    private String pname;

    private String pdesc;

    private int price;

    private List<String> imageNames;


}
