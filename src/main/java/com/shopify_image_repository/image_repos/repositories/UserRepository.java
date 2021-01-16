package com.shopify_image_repository.image_repos.repositories;

import com.shopify_image_repository.image_repos.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
    extends CrudRepository<User, Long>
{
}
