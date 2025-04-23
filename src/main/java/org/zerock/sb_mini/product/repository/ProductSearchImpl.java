package org.zerock.sb_mini.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.zerock.sb_mini.product.dto.PageRequestDTO;
import org.zerock.sb_mini.product.dto.PageResponseDTO;
import org.zerock.sb_mini.product.dto.ProductListAllDTO;
import org.zerock.sb_mini.product.dto.ProductListDTO;
import org.zerock.sb_mini.product.entities.ProductEntity;
import org.zerock.sb_mini.product.entities.QProductEntity;
import org.zerock.sb_mini.product.entities.QProductImgEntity;
import org.zerock.sb_mini.product.entities.QProductReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

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
        query.leftJoin(qProductEntity.files, qProductImgEntity);
        query.leftJoin(qProductReviewEntity).on(qProductReviewEntity.product.eq(qProductEntity));
        //검색조건
        query.where(qProductImgEntity.ord.eq(0)
                .and(qProductEntity.delFlag.isFalse()));
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
                qProductImgEntity.fileName.as("fileName"),
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

/*    @Override
    //전체 데이터 조회 - 전체 이미지 다 보여주기
    public PageResponseDTO<ProductListAllDTO> listAllQuerydsl(PageRequestDTO pageRequestDTO) {

        QProductEntity qProductEntity = QProductEntity.productEntity;
        QProductImgEntity qProductImgEntity = QProductImgEntity.productImgEntity;
        QProductReviewEntity qProductReviewEntity = QProductReviewEntity.productReviewEntity;

        JPQLQuery<ProductEntity> query = queryFactory.selectFrom(qProductEntity);

        //join
        query.leftJoin(qProductEntity.images, qProductImgEntity);
        query.leftJoin(qProductReviewEntity).on(qProductReviewEntity.product.eq(qProductEntity));
        //검색조건
        query.where(qProductEntity.delFlag.isFalse());
        //정렬조건
        query.groupBy(qProductEntity);
        query.orderBy(new OrderSpecifier<>(Order.DESC, qProductEntity.pno));
        //페이징
        query.offset(pageRequestDTO.getOffset());
        query.limit(pageRequestDTO.getLimit());

        //바로 dto를 못뽑음 - (Tuple로 묶어서 상품 + 평균 평점 + 리뷰 수 추출 -> 그걸 DTO로 가공해서 반환)
        //Tuple = ( title, content, writer)
        JPQLQuery<Tuple> tupleQuery = query.select(
                qProductEntity,
                qProductReviewEntity.score.coalesce(0).avg().as("avgRating"),
                qProductReviewEntity.countDistinct().as("reviewCnt"));

        List<Tuple> tupleList = tupleQuery.fetch();

//        tupleList.forEach(tuple -> {
//
//            ProductEntity product = tuple.get(0, ProductEntity.class);
//            log.info(product);
//            log.info(product.getImages());
//
//            log.info("----------------------");
//
//        });

        List<ProductListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            ProductEntity product = tuple.get(0, ProductEntity.class);
            List<String> imageNames = product.getImages().stream().map(productImage -> productImage.getImgName()).collect(Collectors.toUnmodifiableList());
            double avgRating = tuple.get(1, double.class);
            long reviewCnt = tuple.get(2, long.class);

            ProductListAllDTO productListAllDTO = new ProductListAllDTO();

            productListAllDTO.setPno(product.getPno());
            productListAllDTO.setPname(product.getPname());
            productListAllDTO.setPrice(product.getPrice());
            productListAllDTO.setImgNames(imageNames);
            productListAllDTO.setAvgRating(avgRating);
            productListAllDTO.setReviewCnt(reviewCnt);

            return productListAllDTO;

        }).collect(Collectors.toUnmodifiableList());

        long total = tupleQuery.fetchCount();

        return PageResponseDTO.<ProductListAllDTO>withAll()
                .dtoList(dtoList)
                .total((int) total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }*/

}
