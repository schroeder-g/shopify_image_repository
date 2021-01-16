package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.models.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "imageService")
public class ImageServicesImpl implements ImageServices
{
    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Image findById() {
        return null;
    }

    @Override
    public Image save(long imageid) {
        return null;
    }

    @Override
    public Image update(long imageid, Image updateImage) {
        return null;
    }

    @Override
    public void deleteAllImages() {

    }
}
