package com.shopify_image_repository.image_repos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/** Authorization - always clients to access - username / password => client id / client secret
 Authentication - users - username / password. Restricts specific resources based on roles.
 Access token - identifies the user */

/**
 * This class enables and configures the Authorization Server. The class is also responsible for granting authorization to the client.
 * This class is responsible for generating and maintaining the access tokens.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
    /* Client Id and Secret are the user name and pw for the client application.
       They are read from the environment variables on your machine */
    static final String CLIENT_ID = System.getenv("OAUTHCLIENTID");
    static final String CLIENT_SECRET = System.getenv("OAUTHCLIENTSECRET");
    static final String GRANT_TYPE_PASSWORD = "password";

    /**
     * We are using the client id and client security combination to authorize the client.
     * The client id and security can be base64 encoded into a single API key or code
     */
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String SCOPE_WRITE = "write";
    static final String SCOPE_READ = "read";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = -1;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.inMemory() // storing in memory is fast and secure
                .withClient(CLIENT_ID)
                .secret(encoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE)
                .scopes(SCOPE_WRITE, SCOPE_READ, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    {
        //connects spring with our configurations
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
        // sets where client goes to retrieve access token
        endpoints.pathMapping("/oauth/token", "/login");
    }
}
