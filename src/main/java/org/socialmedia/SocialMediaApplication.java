package org.socialmedia;

import org.socialmedia.model.Role;
import org.socialmedia.model.User;
import org.socialmedia.repository.mysql.RoleRepository;
import org.socialmedia.repository.mysql.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EntityScan("org.socialmedia.model")
@EnableJpaRepositories(basePackages = "org.socialmedia.repository.mysql")
@EnableMongoRepositories(basePackages = "org.socialmedia.repository.mongodb")
@EnableTransactionManagement
public class SocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        return argus -> {
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return ;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User("admin", passwordEncoder.encode("password") , roles);
            userRepository.save(admin);
        };
    }

}
