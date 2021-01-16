package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl
    implements UserServices
{
    @Autowired
    private UserRepository userRepos;

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return null;
    }

    @Override
    public User findUserById(long id) {
        return null;
    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user, long id) {
        return null;
    }
}
