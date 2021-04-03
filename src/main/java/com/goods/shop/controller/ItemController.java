package com.goods.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goods.shop.dto.response.ApiResponseDTO;
import com.goods.shop.service.impl.ImageStorageServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

	private static final Logger log = LogManager.getLogger(ItemController.class);
	
	@Autowired
	private ImageStorageServiceImpl imageStorageService;
	
	@PostMapping("/upload-image")
	public ApiResponseDTO<List<String>> uploadImage(
			@RequestParam(value = "files") MultipartFile [] files
		) throws IOException {
		
		List<String> uploadedFiles = imageStorageService.save(files);
		
		log.info("Response Data : ["+uploadedFiles+"]");
		
		return ApiResponseDTO.createOK(uploadedFiles);
	}
	
	@GetMapping("/preImage/{image}")
	@ResponseBody
	public ResponseEntity<Resource> preImage(@PathVariable("image") String image) throws IOException {
		Resource resource = imageStorageService.load(image);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");


        return ResponseEntity.ok()
                .headers(header)
                .contentLength(resource.getFile().length())
                .body(resource);
    }
	
	@PostMapping("")
	public ApiResponseDTO<String> create() {
		
		
		
		return null;
	}
	
	
}
