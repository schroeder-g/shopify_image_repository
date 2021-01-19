package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.exceptions.ResourceNotFoundException;
import com.shopify_image_repository.image_repos.models.Image;
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

    /**
     * Returns All image objects in the database.
     * <br>Example: <a href="http://localhost:2021/images/find/8"></a>
     *
     * @return JSON image object with a status of OK
     * @see ImageServices#findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/all_pics", produces = "application/json")
    public ResponseEntity<?> getAllListings()
    {
        List<Image> allImages = imgServ.findAll();
        return new ResponseEntity<>(allImages, HttpStatus.OK);
    }

    /**
     * Returns the image object associated with a specific ID.
     * <br>Example: <a href="http://localhost:2021/images/find/8"></a>
     *
     * @param  imageid
     * @return JSON image object with a status of OK
     * @see ImageServices#findById(long) (String)
     */
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
     * Returns a list of all of a user's public images.
     * <br>Example: <a href="http://localhost:2021/images/alexgoncalves/get-public-pics"></a>
     *
     * @param  username
     * @return JSON list of all public images a user owns with a status of OK
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

    /**
     * Edits Image Objects to
     * <br>Example: <a href="http://localhost:2021/images/8/updateprivacy">http://localhost:2021/images/8/updateprivacy/a>
     *
     * @param  imageid
     * @return A status of OK
     * @see ImageServices#update(long, Image)
     */
    @PatchMapping(value = "/{imageid}/updateprivacy", consumes = {"application/json"})
    public ResponseEntity<?>  setPicturePrivacy(@PathVariable long imageid, @RequestBody Image updateBody)
     {
         imgServ.update(imageid, updateBody);

         return new ResponseEntity<>(HttpStatus.OK);
     }



}

