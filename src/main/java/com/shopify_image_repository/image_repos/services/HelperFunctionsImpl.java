package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions
{
    @Autowired
    UserRepository userRepos;

    @Override
    public boolean isAuthorizedToMakeChange(String username) {
        Authentication authentication = SecurityContextHolder
                                        .getContext().getAuthentication();

        if (username.equalsIgnoreCase(authentication.getName()
                                        .toLowerCase()) || authentication.getAuthorities()
                                        .contains(new SimpleGrantedAuthority("ADMIN")))
        {
            return true;
        } else
        {
            throw new ResourceNotFoundException(authentication.getName() +
                                                " not authorized to make this change.");
        }
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                                        .getContext().getAuthentication();

        return userRepos.findByUsername(authentication.getName());
    }
}
