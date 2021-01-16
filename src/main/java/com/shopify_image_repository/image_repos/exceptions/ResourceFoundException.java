package com.shopify_image_repository.image_repos.exceptions;

public class ResourceFoundException
    extends RuntimeException
{
    public ResourceFoundException(String message)
    {
        super("Resource Found Exception from the PicVault Application: " + message);
    }
}
