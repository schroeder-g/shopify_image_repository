package com.shopify_image_repository.image_repos.repositories;

import com.shopify_image_repository.image_repos.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
    extends CrudRepository<User, Long>
{
    /**
     * Find a user based off of username
     *
     * @param username the name (String) of user you seek
     * @return the first user object with the name you seek
     */
    User findByUsername(String username);
}
