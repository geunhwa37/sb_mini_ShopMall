package org.zerock.sb_mini.product.repository;

import org.zerock.sb_mini.product.dto.PageRequestDTO;
import org.zerock.sb_mini.product.dto.PageResponseDTO;
import org.zerock.sb_mini.product.dto.ProductListAllDTO;
import org.zerock.sb_mini.product.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> listQuerydsl(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListAllDTO> listAllQuerydsl(PageRequestDTO pageRequestDTO);
}
