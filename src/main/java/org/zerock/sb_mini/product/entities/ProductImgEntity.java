package org.zerock.sb_mini.product.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
public class ProductImgEntity {

    //product_pno는 자동으로 만들어짐

    private String imgName;
    private int ord;

}
