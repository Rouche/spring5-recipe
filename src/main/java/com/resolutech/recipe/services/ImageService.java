package com.resolutech.recipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long aLong, MultipartFile file);
}
