package com.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration

@EnableOAuth2Sso
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {
}
