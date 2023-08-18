package OnlineShopping.config;

import OnlineShopping.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // allow cors, disable csrf
        http = http.cors()
                .and()
                    .csrf()
                    .disable();
        // session management
        http = http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and();
        // jwt token filter
        http = http
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // endpoint permissions
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/product/**").hasAuthority("ADMIN")
                .requestMatchers("/auth/**",
                        "/deal/**",
                        "/user/**",
                        "/purchase/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        // logout handler
        http.logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .invalidateHttpSession(true);

        
        return http.build();
    }
}
