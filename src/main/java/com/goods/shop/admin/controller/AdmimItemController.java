package com.goods.shop.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goods.shop.admin.dto.ItemDTO;
import com.goods.shop.admin.dto.response.ApiResponseDTO;
import com.goods.shop.admin.service.AdminItemService;
import com.goods.shop.admin.service.impl.ImageStorageServiceImpl;
import com.goods.shop.model.Item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/adm/items")
public class AdmimItemController {

	private static final Logger log = LogManager.getLogger(AdmimItemController.class);
	
	private final ImageStorageServiceImpl imageStorageService;
	
	private final AdminItemService itemService;
	
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
	
	/**
	 * 상품 리스트 가져오기 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ItemDTO>> getItems() {
		List<ItemDTO> resList = itemService.getItems();
		
		return ResponseEntity.ok(resList);
	}
	
	/**
	 * 상품 데이터 생성
	 * @param create
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ItemDTO> createItem(
			@RequestBody ItemDTO create
			) {
		
		return ResponseEntity.ok(itemService.createItem(create));
	}
	
	
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemDTO> getItem(
			@PathVariable("itemId") Long itemId
			) {
		ItemDTO res = itemService.getItem(itemId);
		
		return ResponseEntity.ok(res);
	}
	
	@PutMapping("/{itemId}")
	public ResponseEntity<ItemDTO> updateItem(
			@PathVariable("itemId") Long itemId,
			@RequestBody ItemDTO update
			) {
		ItemDTO res = itemService.updateItem(itemId, update);
		
		
		return ResponseEntity.ok(res);
	}
	
	
	@DeleteMapping("/{itemId}")
	public ResponseEntity<String> deleteItem(
			@PathVariable("itemId") Long itemId
		) throws IOException {
		
		log.info("Delete ItemId : ["+itemId+"]");
		
		String message = itemService.deleteItem(itemId);
		
		return ResponseEntity.ok(message);
	}
	
}
