package com.resolutech.recipe.controllers;

import com.resolutech.recipe.commands.RecipeCommand;
import com.resolutech.recipe.services.ImageService;
import com.resolutech.recipe.services.RecipeService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collector;

/**
 * Created by jt on 7/3/17.
 */
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    //@important return image data
    @GetMapping("/recipe/{id}/recipeimage")
    public void getRecipeImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        Byte[] content = recipeCommand.getImage();
        final byte[] bytesPrim = new byte[content.length];
        for(int i = 0; i < content.length; ++i) {
            bytesPrim[i] = content[i].byteValue();
        }

        response.setContentType("image/jpeg");
        response.setContentLength(bytesPrim.length);
        InputStream bais = new ByteArrayInputStream(bytesPrim);
        IOUtils.copy(bais, response.getOutputStream());
    }
}