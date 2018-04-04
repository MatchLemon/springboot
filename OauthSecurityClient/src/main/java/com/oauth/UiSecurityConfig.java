package com.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Sso
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    OAuth2ClientContext oauth2ClientContext;
    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/", "/login**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                //.and().exceptionHandling()
//                //.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
//                //.logout().logoutSuccessUrl("/").permitAll()
//                //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//
//                .and().exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
//                .logoutSuccessUrl("/").permitAll();
//                //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated().and().exceptionHandling()
                .and().logout().logoutSuccessUrl("/").permitAll().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                //.and().addFilterBefore(new CompositeFilter(ssoFilter( new ClientResources() ,"/login/")), BasicAuthenticationFilter.class);

    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
                client.getClient().getClientId());
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
        return oAuth2ClientAuthenticationFilter;
    }
}
class ClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
