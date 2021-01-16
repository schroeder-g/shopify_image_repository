package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.models.Image;

import java.util.List;

public interface ImageServices
{
    List<Image> findAll();

    Image findById();

    Image save(long imageid);

    Image update(long imageid, Image updateImage);

    void deleteAllImages();
}
