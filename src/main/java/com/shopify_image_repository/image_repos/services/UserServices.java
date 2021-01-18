package com.shopify_image_repository.image_repos.services;

import com.shopify_image_repository.image_repos.models.User;

import java.util.List;

public interface UserServices {
    /**
     * Returns a list of all the Users
     *
     * @return List of Users. If no users, empty list.
     */
    List<User> findAll();

    /**
     * Returns the user with the given primary key.
     *
     * @param id The primary key (long) of the user you seek.
     * @return The given User or throws an exception if not found.
     */
    User findUserById(long id);

    User findUserByName(String username);

    void delete(long id);

    /**
     * Deletes all record and their associated records from the database (for seed data)
     */
    public void deleteAll();
    /**
     * Given a complete user object, saves that user object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param user the user object to be saved
     * @return the saved user object including any automatically generated fields
     */
    User save(User user);

    /**
     * Updates the provided fields in the user record referenced by the primary key.
     * <p>
     * Regarding Role and Useremail items, this process only allows adding those. Deleting and editing those lists
     * is done through a separate endpoint.
     *
     * @param user just the user fields to be updated.
     * @param id   The primary key (long) of the user to update
     * @return the complete user object that got updated
     */
    User update(
            User user,
            long id);

}
