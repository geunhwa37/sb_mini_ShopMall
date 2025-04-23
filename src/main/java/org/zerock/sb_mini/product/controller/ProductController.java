package org.zerock.sb_mini.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.zerock.sb_mini.product.dto.*;
import org.zerock.sb_mini.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //리스트
    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ProductListDTO> result = productService.list(pageRequestDTO);
        return ResponseEntity.ok(result);
    }
    //조회
    @GetMapping("read/{pno}")
    public ResponseEntity<ProductReadDTO> read(@PathVariable long pno) {

        ProductReadDTO dto = productService.read(pno);
        return ResponseEntity.ok(dto);
    }
    //등록
    @PostMapping(value = "add", consumes = "multipart/form-data")
    public ResponseEntity<Long> add(@ModelAttribute ProductAddDTO dto) { // JSON을 자동으로 파싱해서 ProductAddDTO 객체에 담아줌

        Long pno = productService.add(dto);
        return ResponseEntity.ok(pno); // 200 OK + 생성된 상품 번호
    }
    //수정
    @PostMapping(value = "modify/{pno}", consumes = "multipart/form-data")
    public ResponseEntity<Void> modify(@PathVariable long pno, @ModelAttribute ProductModifyDTO dto) {

        dto.setPno(pno);
        productService.modify(dto);
        return ResponseEntity.ok().build(); // 200 OK만 보냄
    }
    //삭제
    @PostMapping("remove/{pno}")
    public ResponseEntity<Void> remove(@PathVariable long pno) {

        productService.remove(pno);
        return ResponseEntity.ok().build();
    }






}
