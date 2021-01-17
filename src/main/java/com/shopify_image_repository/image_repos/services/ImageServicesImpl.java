package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.Image;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.repositories.ImageRepository;
import com.shopify_image_repository.image_repos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "imageService")
public class ImageServicesImpl implements ImageServices
{
    @Autowired
    ImageRepository imgRepos;

    @Autowired
    UserRepository userRepos;

    @Autowired
    HelperFunctions helperFunctions;

    @Override
    public List<Image> findAll()
    {
        List<Image> images = new ArrayList<>();

        imgRepos.findAll().iterator().forEachRemaining(images :: add);

        return images;
    }

    @Override
    public Image findById(long imageid) throws ResourceNotFoundException
    {
        Image img = imgRepos.findById(imageid)
                .orElseThrow(() -> new ResourceNotFoundException("Picture " + imageid + " is unavailable."));
        return img;
    }

    @Override
    public Image save(Image image) throws ResourceNotFoundException
    {
        Image newImage = new Image();

        // Prevents the image repository from handling an entity without a primary key
        if(image.getImageId() != 0)
        {
            imgRepos.findById(image.getImageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Picture " + image.getImageId() + " wasn't found."));
            newImage.setImageId(image.getImageId());
        }

        newImage.setOwner(image.getOwner());
        newImage.setTitle(image.getTitle());
        newImage.setUrl(image.getUrl());
        newImage.setIsPrivate(image.getIsPrivate());

        return imgRepos.save(newImage);
    }

    @Override
    public Image update(long imageid, Image image)
    {
        User currentUser = image.getOwner();

        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            Image updatingimage = findById(imageid);

            if (image.getUrl() != null)
            {
                updatingimage.setUrl(image.getUrl());
            }

            if (image.getTitle() != null)
            {
                updatingimage.setTitle(image.getTitle());
            }

            if (image.getIsPrivate() != null)
            {
                updatingimage.setUrl(image.getUrl());
            }

            return imgRepos.save(updatingimage);
        } else {
            throw new ResourceNotFoundException("You don't have permission to edit this image");
        }
    }

    @Override
    public void deleteAllImages() { imgRepos.deleteAll(); }
}
