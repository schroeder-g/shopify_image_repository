package com.shopify_image_repository.image_repos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    private static final String Resource_ID  = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(Resource_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http)
            throws Exception
    {

        // disable the creation and use of Cross Site Request Forgery Tokens.
        // These tokens require coordination with the front end client that is beyond the scope of this class.
        // See https://www.yawintutor.com/how-to-enable-and-disable-csrf/ for more information
        http.csrf().disable();

        // this disables all of the security response headers. This is necessary for access to the H2 Console.
        // Normally, Spring Security would include headers such as
        //     Cache-Control: no-cache, no-store, max-age=0, must-revalidate
        //     Pragma: no-cache
        //     Expires: 0
        //     X-Content-Type-Options: nosniff
        //     X-Frame-Options: DENY
        //     X-XSS-Protection: 1; mode=block
        http.headers()
                .frameOptions()
                .disable();

        // This application implements its own logout procedure so disable the one built into Spring Security
        http.logout().disable();

        /** The fun part: who can make what requests
         // our antMatchers control which user roles have access to which endpoints.
         // we must order our antmatchers from most restrictive to least restrictive.
         // So restrict at method level before restricting at endpoint level.
         // permitAll = everyone and their brother
         // authenticated = any authenticated, signed in, user */
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources",
                        "/swagger-resource",
                        "/swagger-ui.html"
                        )
                .permitAll()
                .antMatchers(HttpMethod.GET, "/users")
                .hasAnyRole("ADMIN")
                .antMatchers("/logout",
                        "/oauth/revoke-token",
                        "/images/**"
                )
                .authenticated();



    }
}
