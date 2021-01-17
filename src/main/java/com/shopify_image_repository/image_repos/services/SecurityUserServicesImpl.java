package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "securityUserService")
public class SecurityUserServicesImpl
    implements UserDetailsService
{
    @Autowired
    private UserRepository userRepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s)
            throws ResourceNotFoundException
    {
        User user = userRepos.findByUsername(s.toLowerCase());
        if (user == null)
        {
            throw new ResourceNotFoundException("Invalid username or password: " + s.toLowerCase() + " | " + "Lists Users: " + userRepos.findAll());

        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getAuthority());
    }
}
