package com.shopify_image_repository.image_repos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String Resource_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(Resource_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http)
            throws Exception {

        // disable the creation and use of Cross Site Request Forgery Tokens.
        http.csrf().disable();

        // this disables all of the security response headers. This is necessary for access to the H2 Console.
        http.headers()
                .frameOptions()
                .disable();

        // This application implements its own logout procedure so disable the one built into Spring Security
        http.logout().disable();

        // http.requiresChannel().anyRequest().requiresSecure(); required for https

        // Controls Application Permissions.
        // permitAll = everyone and their brother
        // authenticated = any authenticated, signed in, user
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/h2/**",
                        "/login",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui/index.html",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/createnewuser"
                )
                .permitAll()
                .antMatchers("/logout",
                        "/oauth/revoke-token",
                        "/images/**"
                )
                .authenticated()
                .antMatchers(HttpMethod.PATCH,
                        "/images**")
                .authenticated()
                .antMatchers("/roles/**")
                .hasAnyRole("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

    }
}
