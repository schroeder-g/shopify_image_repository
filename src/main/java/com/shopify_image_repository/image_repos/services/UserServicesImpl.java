package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.Role;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.models.UserRoles;
import com.shopify_image_repository.image_repos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServicesImpl
    implements UserServices
{
    @Autowired
    private UserRepository userRepos;

    @Autowired
    private RoleServices roleServices;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();

        userRepos.findAll()
                .iterator()
                .forEachRemaining(list :: add);
        return list;
    }

    @Override
    public User findUserById(long id) throws
                ResourceNotFoundException
    {
        return userRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + "could not be found"));
    }

    @Override
    public User findUserByName(String username) {
        return userRepos.findByUsername(username);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found"));
        userRepos.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        userRepos.deleteAll();
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userRepos.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase().replaceAll(" ",  ""));
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setImages(user.getImages());
        newUser.setEmail(user.getEmail());

        newUser.setRoles(new HashSet<>());
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleServices
                    .findRoleById(ur.getRole()
                    .getRoleid());
            newUser.getRoles()
                    .add(new UserRoles(newUser, addRole));
        }
        System.out.println("saving user " + newUser.getRoles());
        return userRepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);

        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername().toLowerCase());
            }
            if (user.getEmail() != null)
            {
                currentUser.setEmail(user.getEmail());
            }

            return userRepos.save(currentUser);
        } else
        {
            throw new ResourceNotFoundException("You are not currently authorized to make those changes");
        }
    }
}
