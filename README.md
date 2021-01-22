# Shopify PicVault

This backend, created by Alexander Goncalves for the Shopify Backend Internship Challenge, demonstrates his capacity to design functional databases with Authorization and Authentication capabilities.

For the documentation on all endpoints, navigate [**here**](https://asg-shopify.herokuapp.com/swagger-ui/index.html). 

To test the capabilities of this repo, follow these steps:

1. **Create environment variables** on your local machine: *OAUTHCLIENTID* and *OAUTHCLIENTSECRET*. Their values don't matter, in this case, but the application won't run if they aren't present.

2. **Create a new user**. In Postman or your favorite API client, make a POST request to *https://asg-shopify.herokuapp.com/users/register*. The request body should have the following shape:

    {
    
        "username": "example",
        "password": "password",
        "email": "123@gmail.com"
        
    }

3. **Generate an access token.** Using your new credentials, request a token with the following Authentication settings:


    {
        Authorization: Oauth2.0,
        Grant Type: Resource Owner Password Credentials,
        username: your_username,
        password": your_password,
        access token url == http://asg-shopify.herokuapp.com/login,
        id and secret: leave blank,
        credentials: As Basic Auth Header    
    }

4. Once the token is granted, you will gain access to the image and user controllers. To test the image privacy features, try the following series of requests:

    a.) **Create an image**. POST the object  below to the endpoint */images/new-pic*, and you should get a 200 response:
    
        {
             "title": "Shopify Rules!",
             "url": "https://jordiob.com/amazon-tools/wp-content/uploads/2020/08/shopify-apps.png",
             "isPrivate": false           
        }
    
    b.) **Confirm success** with a GET call to /images/**:your_username**/get-public-pics; your picture should be there.
    
    c.) **Change the private Status** of the image by making a PATCH request to  /images/**:image_id**/updateprivacy. The request body can be as simple as:
        
        {          
            "isPrivate": "true"
        }
    
    d.) Make another GET call to /images/**:your_username**/get-public-pics, and the image should be gone. 
    
    ...) You'll note that you don't have permissions to update other user's images. Try to replicate step C for the user Alex Goncalves at: images/**9**/updateprivacy, and note that you don't have those privileges; a FORBIDDEN status should be returned.

#USERS
Method | Endpoint | Description | Required Data
--- | --- | --- | ---
POST | /users/register | Creates a new user account | `username, password, email` -- All Strings
POST | /login | Logs in to account and fetches token | `username, password` -- both Strings
GET | /users | Returns a list of all Users
GET | /users/:userid | Returns an individual User object when searched by userid
GET | /username/:username | Returns an individual User object when searched by username

## IMAGES CRUD
Method | Endpoint | Description | Required Data
--- | --- | --- | ---
POST | /images/u/:userid/t/:title | Creates a new Image for a specific User
GET | /images | Returns all Todo Lists and their associated Items
GET | /images/:imageid | Returns a specific image object by its id.
PATCH | /images/:imageid/updateprivacy | Toggle boolean isPrivate value, which determines whether other users can see this image.
