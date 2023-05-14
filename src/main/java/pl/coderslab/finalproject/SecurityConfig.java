package pl.coderslab.finalproject;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(r->r
                .requestMatchers("/").permitAll()
                .anyRequest()
//                        .authenticated()          //TODO change for production
                        .permitAll())
                .formLogin();
        return http.build();
    }
}