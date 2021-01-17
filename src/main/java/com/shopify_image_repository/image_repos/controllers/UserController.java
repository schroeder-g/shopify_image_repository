package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.services.HelperFunctions;
import com.shopify_image_repository.image_repos.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserServices useServ;

    @Autowired
    private HelperFunctions helpFuncs;

    //#region GET ALl Current User info
    /**
     * Returns the User record for the currently authenticated user based off of the supplied access token
     * <br>Example: <a href="http://localhost:2019/users/getuserinfo">http://localhost:2019/users/getuserinfo</a>
     *
     * @param authentication The authenticated user object provided by Spring Security
     * @return JSON of the current user. Status of OK
     * @see HelperFunctions#getCurrentUser() (String) UserService.findByName(authenticated user)
     */
//    @ApiOperation(value = "returns the currently authenticated user",
//        response = User.class)
    @GetMapping(value = "/getuserinfo",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = useServ.findUserByName(authentication.getName());
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }
    //#endregion

    //#region GET All Users
    /**
     * Returns a list of all users
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all users with a status of OK
     * @see UserServices#findAll() UserService.findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users",
            produces = "application/json")
    public ResponseEntity<?> listAllUsers(Authentication authentication)
    {
        List<User> myUsers = useServ.findAll();
        return new ResponseEntity<>(myUsers,
                HttpStatus.OK);
    }
    //#endregion

    //#region GET User by ID
    /**
     * Returns a single user based off a user id number
     * <br>Example: http://localhost:2019/users/user/7
     *
     * @param userId The primary key of the user you seek
     * @return JSON object of the user you seek
     * @see UserServices#findUserById(long) UserService.findUserById(long)
     */
    @GetMapping(value = "/user/{userId}",
            produces = "application/json")
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = useServ.findUserById(userId);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }
    //#endregion

}
