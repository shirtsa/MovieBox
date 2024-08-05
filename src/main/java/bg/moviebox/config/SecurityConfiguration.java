package bg.moviebox.config;

import bg.moviebox.repository.UserRepository;
import bg.moviebox.service.impl.MovieBoxUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                // All static resources which are situated in js, images, css are available for anyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // Allow anyone to see the home page, registration page and the login form
                        .requestMatchers("/", "/users/login", "/users/register", "users/login-error", "/api/convert").permitAll()
                // All other requests are authenticated.
                        .requestMatchers("/productions/add", "/celebrities/add", "/news/add").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    // redirect to login page when we access something which is not allowed.
                    formLogin
                            .loginPage("/users/login")
                            // The names of the input fields (login.html)
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            // The URL where we should POST something in order to perform the logout(empty request)
                            .logoutUrl("/users/logout")
                            // where to go when logged out.
                            .logoutSuccessUrl("/")
                            // invalidate the HTTP session (delete)
                            .invalidateHttpSession(true);
                }
        ).build();
    }

//    rememberMe()
//    .rememberMeParameter('remember')
//    .key('remember me Encryption key')
//    .rememberMeCookieName('rememberMeCookieName')
//    .tokenValiditySeconds(10000)

    //This service translates the MovieBox users and roles
    // to representation which spring security understands.
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new MovieBoxUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
