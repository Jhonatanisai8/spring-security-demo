package com.isai.spring_security_demo.config;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.isai.spring_security_demo.services.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        // (SessionCreationPolicy.STATELESS se guarda la sesion en memoria
        return httpSecurity
                .csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // configurar los enpoints publicos
                    http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll();
                    // configurar los enpoints privados
                    http.requestMatchers(HttpMethod.POST, "/auth/post")
                            .hasAnyAuthority("CREATED", "READ");
                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch")
                            .hasAnyAuthority("REFACTOR");
                    // validando por el
                    http.requestMatchers(HttpMethod.DELETE, "/auth/delete")
                            .hasRole("ADMIN");
                    // configurar los enpoints no especificados
                    // http.anyRequest().denyAll(); //rechaza todo
                    http.anyRequest().authenticated(); // permite mientras estas autenticado
                })
                .build();
    }

    /*
     * @Bean
     * public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
     * throws Exception {
     * return httpSecurity
     * .csrf(crsf -> crsf.disable())
     * .httpBasic(Customizer.withDefaults())
     * .sessionManagement(sesion ->
     * sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no
     * // guardar
     * // la sesion
     * // en
     * // menoria
     * .build();
     * }
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImp userDetailsServiceImp) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsServiceImp);
        return authenticationProvider;
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // List<UserDetails> userDetails = List.of(
    // User
    // .withUsername("jhona")
    // .password("1234")
    // .roles("ADMIN")
    // .authorities("READ", "CREATED")
    // .build(),
    // User
    // .withUsername("eli")
    // .password("1234")
    // .roles("USER")
    // .authorities("READ")
    // .build());
    // return new InMemoryUserDetailsManager(userDetails);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
