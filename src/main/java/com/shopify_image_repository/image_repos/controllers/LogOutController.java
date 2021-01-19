package com.shopify_image_repository.image_repos.controllers;

import com.shopify_image_repository.image_repos.services.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {
    @Autowired
    private TokenStore tokenStore;

    /**
     * Logs User out of the application by revoking their authorization token.
     * <br>Example: <a href="http://localhost:2021/logout">http://localhost:2021/logout</a>
     *
     * @return A status of OK
     * @see com.shopify_image_repository.image_repos.config.AuthorizationServerConfig
     * @see com.shopify_image_repository.image_repos.config.DataSourceConfig
     * @see com.shopify_image_repository.image_repos.config.ResourceServerConfig
     */
    @RequestMapping(value = {"/oauth/revoke-token", "/logout"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.GONE)
    public void logoutSelf(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}
