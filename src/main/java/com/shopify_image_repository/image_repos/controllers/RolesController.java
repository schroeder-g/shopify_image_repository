package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.models.Role;
import com.shopify_image_repository.image_repos.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * The entry point for clients to access role data
 * <p>
 * Note: we cannot update a role
 * we cannot update a role
 * working with the "non-owner" object in a many to many relationship is messy
 * we will be fixing that!
 */
@RestController
@RequestMapping("/roles")
public class RolesController
{
    /**
     * Using the Role service to process Role data
     */
    @Autowired
    RoleServices roleServ;

    /**
     * List of all roles
     * <br>Example: <a href="http://localhost:2019/roles/roles">http://localhost:2019/roles/roles</a>
     *
     * @return JSON List of all the roles and their associated users
     * @see RoleServices#findAll() RoleService.findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/roles",
            produces = "application/json")
    public ResponseEntity<?> listRoles()
    {
        List<Role> allRoles = roleServ.findAll();
        return new ResponseEntity<>(allRoles,
                HttpStatus.OK);
    }

    /**
     * The Role referenced by the given primary key
     * <br>Example: <a href="http://localhost:2019/roles/role/3">http://localhost:2019/roles/role/3</a>
     *
     * @param roleId The primary key (long) of the role you seek
     * @return JSON object of the role you seek
     * @see RoleServices#findRoleById(long) RoleService.findRoleById(long)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/role/{roleId}",
            produces = "application/json")
    public ResponseEntity<?> getRoleById(
            @PathVariable
                    Long roleId)
    {
        Role r = roleServ.findRoleById(roleId);
        return new ResponseEntity<>(r,
                HttpStatus.OK);
    }

    /**
     * The Role with the given name
     * <br>Example: <a href="http://localhost:2019/roles/role/name/data">http://localhost:2019/roles/role/name/data</a>
     *
     * @param roleName The name of the role you seek
     * @return JSON object of the role you seek
     * @see RoleServices#findByName(String) RoleService.findByName(String)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/role/name/{roleName}",
            produces = "application/json")
    public ResponseEntity<?> getRoleByName(
            @PathVariable
                    String roleName)
    {
        Role r = roleServ.findByName(roleName);
        return new ResponseEntity<>(r,
                HttpStatus.OK);
    }
}
