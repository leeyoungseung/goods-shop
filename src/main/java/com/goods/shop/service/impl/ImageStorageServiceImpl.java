package com.goods.shop.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.goods.shop.service.FilesStorageService;

@Service
public class ImageStorageServiceImpl implements FilesStorageService {

	private static final Logger log = LogManager.getLogger(ImageStorageServiceImpl.class);
			
	//private final Path root = Paths.get(filePath);

	@Value("${property.app.static-path}")
	private String staticPath;
	
	@Value("${property.app.upload-path}")
	private String uploadPath;

	@Override
	public void init(Path dir) {
		try {
			Files.createDirectory(dir);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public List<String> save(MultipartFile[] files) {
		try {
			List<String> uploadedFiles = new ArrayList<String>();
			log.info("Resource Path : ["+ staticPath+uploadPath+"]");
			String uniqImgeFileDir = staticPath+uploadPath;
			Path root = Paths.get(uniqImgeFileDir);
			//init(root);
			
			int cnt =0;
			for (MultipartFile file : files) {
				String fileName = 
						new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					    + "_"+
					    file.getOriginalFilename();
				
				log.info("[ "+cnt+" ]"+ fileName);
				
				Files.copy(file.getInputStream(), root.resolve(fileName));
				uploadedFiles.add(fileName);
				
				cnt++;
			}

			return uploadedFiles;
			
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
	
	@Override
	public Resource load(String filename) {
		
		log.info("Load File :"+filename);
		
		try {
			String uniqImgeFileDir = staticPath+uploadPath;
			Path root = Paths.get(uniqImgeFileDir);
			
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}
	
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getOne(String image) {
		// TODO Auto-generated method stub
		return null;
	}



//	@Override
//	public void deleteAll() {
//		FileSystemUtils.deleteRecursively(root.toFile());
//	}
//
//	@Override
//	public Stream<Path> loadAll() {
//		try {
//			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
//		} catch (IOException e) {
//			throw new RuntimeException("Could not load the files!");
//		}
//	}
}
