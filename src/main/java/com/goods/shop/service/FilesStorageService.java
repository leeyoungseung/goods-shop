package com.goods.shop.service;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
	  public void init(Path dir);

	  public List<String> save(MultipartFile[] files);

	  public Resource load(String filename);

	  public void deleteAll();

	  public Stream<Path> loadAll();

	String saveOne(MultipartFile file);

}
