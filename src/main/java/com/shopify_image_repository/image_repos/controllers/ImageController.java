package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.Image;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.services.HelperFunctions;
import com.shopify_image_repository.image_repos.services.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController
{
    @Autowired
    ImageServices imgServ;

    @Autowired
    HelperFunctions helpFuncs;

    //#region GET All Listings
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/images/all_pics", produces = "application/json")
    public ResponseEntity<?> getAllListings()
    {
        List<Image> allImages = imgServ.findAll();
        return new ResponseEntity<>(allImages, HttpStatus.OK);
    }
    //#endregion

    //#region GET Listing by ID
   @GetMapping(value = "/find/{imageid}", produces = "application/json")
   public ResponseEntity<?> getImageById(@PathVariable long imageid)
   {
       Image image = imgServ.findById(imageid);
       long currentUserID = helpFuncs.getCurrentUser().getUserid();
       if ( currentUserID != image.getOwner().getUserid()){
           return new ResponseEntity<>(new ResourceNotFoundException("You don't have permission to view this image."), HttpStatus.FORBIDDEN);
       }
       return  new ResponseEntity<>(image, HttpStatus.OK);
   }
    //#endregion

   //#region PATCH Toggle Private
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/images/{imageid}/updateprivacy", consumes = "application/json")
    public ResponseEntity<?>  setPicturePrivacy(@PathVariable long imageid, @RequestBody Image image)
     {
         Image update_hidden = imgServ.findById(imageid);
         long currentUserID = helpFuncs.getCurrentUser().getUserid();
         if ( currentUserID != image.getOwner().getUserid()){
             return new ResponseEntity<>("You don't have permission to update this image.", HttpStatus.FORBIDDEN);
         }

         update_hidden.setIsPrivate(!image.getIsPrivate());

         return  new ResponseEntity<>(update_hidden, HttpStatus.OK);
     }
    //#endregion



}

