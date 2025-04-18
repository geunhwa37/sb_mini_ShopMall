package org.zerock.sb_mini.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.zerock.sb_mini.product.dto.PageRequestDTO;
import org.zerock.sb_mini.product.dto.PageResponseDTO;
import org.zerock.sb_mini.product.dto.ProductListDTO;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.entities.QProductEntity;
import org.zerock.sb_mini.product.entities.QProductImgEntity;
import org.zerock.sb_mini.product.entities.QProductReviewEntity;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ProductSearchImpl implements ProductSearch {

    private final JPQLQueryFactory queryFactory;

    @Override
    public PageResponseDTO<ProductListDTO> listQuerydsl(PageRequestDTO pageRequestDTO) {

        QProductEntity qProductEntity = QProductEntity.productEntity;
        QProductImgEntity qProductImgEntity = QProductImgEntity.productImgEntity;
        QProductReviewEntity qProductReviewEntity = QProductReviewEntity.productReviewEntity;

        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(qProductEntity);

        //join
        query.leftJoin(qProductEntity.images, qProductImgEntity);
        query.leftJoin(qProductReviewEntity).on(qProductReviewEntity.product.eq(qProductEntity));
        //검색조건
        query.where(qProductImgEntity.ord.eq(0));
        //정렬조건
        query.groupBy(qProductEntity);
        query.orderBy(new OrderSpecifier<>(Order.DESC, qProductEntity.pno));
        //페이징
        query.offset(pageRequestDTO.getOffset());
        query.limit(pageRequestDTO.getLimit());

        //DTO 변환
        JPQLQuery<ProductListDTO> dtoQuery = query.select(Projections.bean(
                ProductListDTO.class,
                qProductEntity.pno,
                qProductEntity.pname,
                qProductEntity.price,
                qProductEntity.seller,
                qProductImgEntity.imgName.as("imgName"),
                qProductReviewEntity.score.coalesce(0).avg().as("avgRating"), //null 값을 0으로 처리
                qProductReviewEntity.count().as("reviewCnt")
        ));

        List<ProductListDTO> dtoList = dtoQuery.fetch();
        long total = dtoQuery.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .total((int) total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
