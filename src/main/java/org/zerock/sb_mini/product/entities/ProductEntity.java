package org.zerock.sb_mini.product.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@EntityListeners(value = AuditingEntityListener.class) // JPA Auditing 기능 사용 가능하게 함 (예: 생성일, 수정일 자동 관리 등)
@Getter
@ToString(exclude = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;
    private int price;
    private String pdesc;
    private String seller;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "tbl_product_img",
            joinColumns = @JoinColumn(name="product_pno")) //img에 만들어짐
    @Builder.Default // 빌더로 생성 시 images 필드가 null이 되지 않도록 기본값 지정
    private List<ProductImgEntity> images = new ArrayList<>();


    @CreatedDate
    @Column(name = "regDate", updatable = false)
    protected LocalDateTime regDate;

    @LastModifiedDate
    @Column(name ="modDate" )
    protected LocalDateTime modDate;


    public void addImage(String fileName) {

        ProductImgEntity productImgEntity = new ProductImgEntity();
        productImgEntity.setImgName(fileName);
        productImgEntity.setOrd(images.size());

        images.add(productImgEntity);
    }

}
