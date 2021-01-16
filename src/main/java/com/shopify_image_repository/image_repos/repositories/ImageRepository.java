package com.shopify_image_repository.image_repos.repositories;

import com.shopify_image_repository.image_repos.models.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository
    extends CrudRepository<Image, Long>
{

}
