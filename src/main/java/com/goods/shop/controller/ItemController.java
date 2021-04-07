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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goods.shop.dto.ItemDTO;
import com.goods.shop.dto.ItemDTO.Response;
import com.goods.shop.dto.response.ApiResponseDTO;
import com.goods.shop.service.ItemService;
import com.goods.shop.service.impl.ImageStorageServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

	private static final Logger log = LogManager.getLogger(ItemController.class);
	
	private final ImageStorageServiceImpl imageStorageService;
	
	private final ItemService itemService;
	
	@PostMapping("/upload-images")
	public ApiResponseDTO<List<String>> uploadImages(
			@RequestParam(value = "files") MultipartFile [] files
		) throws IOException {
		
		List<String> uploadedFiles = imageStorageService.save(files);
		
		log.info("Response Data : ["+uploadedFiles+"]");
		
		return ApiResponseDTO.createOK(uploadedFiles);
	}
	
	@PostMapping("/upload-image")
	public ApiResponseDTO<String> uploadImage(
			@RequestParam(value = "file") MultipartFile [] files
		) throws IOException {
		
		String uploadedFile = imageStorageService.saveOne(files[0]);
		
		log.info("Response Data : ["+uploadedFile+"]");
		
		return ApiResponseDTO.createOK(uploadedFile);
	}
	
	@GetMapping("/preImage/{image}")
	@ResponseBody
	public ResponseEntity<Resource> preImage(@PathVariable("image") String image) throws IOException {
		log.info("preImage : "+image);
		
		Resource resource = imageStorageService.load(image);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");


        log.info("preImage response Data : "+resource);
        
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(resource.getFile().length())
                .body(resource);
    }
	
	@PostMapping
	public ApiResponseDTO<ItemDTO.ResponseOne> createItem(
			@RequestBody ItemDTO.Create create
			) {
		Response response = itemService.createItem(create);
		
		return ApiResponseDTO.createOK(
				new ItemDTO.ResponseOne(response)
				);
	}
	
	@GetMapping("/{itemId}")
	public ApiResponseDTO<ItemDTO.ResponseOne> getItem(
			@PathVariable("itemId") Long itemId
			) {
		Response response = itemService.getItem(itemId);
		
		return ApiResponseDTO.createOK(
				new ItemDTO.ResponseOne(response)
				);
	}
}
