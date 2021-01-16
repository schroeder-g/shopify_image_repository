package com.shopify_image_repository;

import com.shopify_image_repository.image_repos.services.ImageServices;
import com.shopify_image_repository.image_repos.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData
        implements CommandLineRunner
{
    @Autowired
    private UserServices useServ;

    @Autowired
    private ImageServices imgServ;

    @Autowired

    @javax.transaction.Transactional
    @Override
    public void run(String[] args) throws Exception {
        useServ.deleteAll();
        imgServ.deleteAllImages();
    }
}
