package ro.tuc.ds2020.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApplicationSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){return new BCryptPasswordEncoder();}

}
