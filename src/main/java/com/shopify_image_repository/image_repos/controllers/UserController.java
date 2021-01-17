package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.services.HelperFunctions;
import com.shopify_image_repository.image_repos.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserServices useServ;

    @Autowired
    private HelperFunctions helpFuncs;

}
