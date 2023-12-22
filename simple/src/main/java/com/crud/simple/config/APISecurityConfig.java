package com.crud.simple.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.crud.simple.services.getAdmin;

@EnableWebSecurity
//@Configuration(proxyBeanMethods = false)
public class APISecurityConfig {

  @Autowired
  getAdmin admin;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(admin).passwordEncoder(BcryptEncoder);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests()
        /*.antMatchers(HttpMethod.GET, "/v1/**")
        .hasAuthority(OidcScopes.read)
        .antMatchers(HttpMethod.POST, "/v1/user/create")
        .hasAuthority("ADMIN")
        .antMatchers(HttpMethod.PUT, "/v1/user/update/**")
        .hasAuthority("USER")
        .antMatchers(HttpMethod.DELETE, "/v1/user/delete/**")
        .hasAuthority("ADMIN")*/
        .antMatchers("/login", "/superuser/register","/swagger-ui**", "/oauth2/**", ".well-known/**").permitAll()
        .anyRequest().authenticated().and()
        .logout().invalidateHttpSession(true)
        .clearAuthentication(true).logoutSuccessUrl("/login").deleteCookies("JSESSIONID").permitAll()
        .and().csrf().disable().cors().disable()
        .formLogin(Customizer.withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt())
        .build();

  }

  /* @Bean
	UserDetailsService users() {
		UserDetails user = User.withUsername("admin").password("password").authorities("write").build();
		InMemoryUserDetailsManager user_add = new InMemoryUserDetailsManager();
    user_add.createUser(user);
    return user_add;
	} */


   BCryptPasswordEncoder BcryptEncoder = new BCryptPasswordEncoder();
    
  @Bean
  public PasswordEncoder encoder() {
    //return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    return NoOpPasswordEncoder.getInstance();
    /* Map<String,PasswordEncoder> encoders= new HashMap<>();
    encoders.put("", NoOpPasswordEncoder.getInstance());
    encoders.put("noop", NoOpPasswordEncoder.getInstance());
    encoders.put("bcrypt",new BCryptPasswordEncoder());
    //DelegatingPasswordEncoder passEncoder =  (DelegatingPasswordEncoder)PasswordEncoderFactories.createDelegatingPasswordEncoder();
    DelegatingPasswordEncoder passEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
    passEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
    return passEncoder; */
  }

}
