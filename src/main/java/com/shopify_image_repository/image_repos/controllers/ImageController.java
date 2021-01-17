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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/all_pics", produces = "application/json")
    public ResponseEntity<?> getAllListings()
    {
        List<Image> allImages = imgServ.findAll();
        return new ResponseEntity<>(allImages, HttpStatus.OK);
    }

   @GetMapping(value = "/find/{imageid}", produces = "application/json")
   public ResponseEntity<?> getImageById(@PathVariable long imageid)
   {
       Image image = imgServ.findById(imageid);
       long currentUserID = helpFuncs.getCurrentUser().getUserid();
       if ( currentUserID != image.getOwner().getUserid() && image.getIsPrivate()){
           return new ResponseEntity<>(new ResourceNotFoundException("You don't have permission to view this image."), HttpStatus.FORBIDDEN);
       }
       return  new ResponseEntity<>(image, HttpStatus.OK);
   }

    /**
     * Returns a list of all of a users images that are public
     * <br>Example: <a href="http://localhost:2019/users/users">http://localhost:2019/users/users</a>
     *
     * @return JSON list of all public imags a user owns with a status of OK
     * @see ImageServices#findPublicImagesByUserName(String) () UserService.findAll()
     */
    @GetMapping(value = "/{username}/get-public-pics", produces = "application/json")
    public ResponseEntity<?> getPublicImagesByUserName(@PathVariable String username)
    {
        List<Image> publicImgs = imgServ.findPublicImagesByUserName(username);
        if ( publicImgs.size() < 1){
            return new ResponseEntity<>(new ResourceNotFoundException("Sorry, " + username + " doesn't have any pictures you can see right now"), HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(publicImgs, HttpStatus.OK);
    }

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



}

