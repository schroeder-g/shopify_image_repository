package com.shopify_image_repository.image_repos.exceptions;

public class ResourceNotFoundException
        extends RuntimeException{
    public ResourceNotFoundException(String message)
    {
        super("Error: Resource Not Found in the Pic Vault: " + message);
    }
}
