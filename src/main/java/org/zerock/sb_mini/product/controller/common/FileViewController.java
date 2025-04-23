package org.zerock.sb_mini.product.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.zerock.sb_mini.util.FileUploadUtil;

@Controller
@RequiredArgsConstructor
public class FileViewController {

    private final FileUploadUtil fileUploadUtil;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {

        return fileUploadUtil.getFile(fileName);
    }
}