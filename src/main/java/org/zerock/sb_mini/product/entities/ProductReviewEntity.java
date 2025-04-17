package org.zerock.sb_mini.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class ProductReviewEntity {

    @Id
    private long rno;

    private String reviewer;
    private String comment;
    private String score; //1~5

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    protected LocalDateTime regDate;

    @LastModifiedDate
    @Column(name ="modDate" )
    protected LocalDateTime modDate;

}
