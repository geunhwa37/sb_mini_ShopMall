package org.zerock.sb_mini.product.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductAddDTO {

    private String pname;

    private String pdesc;

    private int price;

    private List<MultipartFile> files = new ArrayList<>();
}
