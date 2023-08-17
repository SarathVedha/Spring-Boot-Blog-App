package com.vedha.blog.configuration;

import com.vedha.blog.entity.RoleEntity;
import com.vedha.blog.entity.UserEntity;
import com.vedha.blog.exception.BlogApiException;
import com.vedha.blog.repository.RoleRepository;
import com.vedha.blog.repository.UserRepository;
import com.vedha.blog.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@AllArgsConstructor
@Slf4j
public class BlogConfiguration implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean()
    public ModelMapper getModelMapper() {

        return new ModelMapper();
    }

    @Override
    public void run(String... args) {

        log.info("Executing Pre-Scripts Start");

        try {

            if (!roleRepository.existsByName(AppConstants.ROLE_ADMIN)) {

                RoleEntity roleAdmin = RoleEntity.builder().name(AppConstants.ROLE_ADMIN).build();
                roleRepository.save(roleAdmin);
            }

            if (!roleRepository.existsByName(AppConstants.ROLE_USER)) {

                RoleEntity roleUser = RoleEntity.builder().name(AppConstants.ROLE_USER).build();
                roleRepository.save(roleUser);
            }

            if (!userRepository.existsByName(AppConstants.ADMIN_NAME) && !userRepository.existsByEmail(AppConstants.ADMIN_EMAIL)) {

                Set<RoleEntity> roleEntities = new HashSet<>();
                RoleEntity roleAdmin = roleRepository.findByName(AppConstants.ROLE_ADMIN).orElseThrow(() -> new BlogApiException("Role Not Found : ".concat(AppConstants.ROLE_ADMIN)));
                RoleEntity roleUser = roleRepository.findByName(AppConstants.ROLE_USER).orElseThrow(() -> new BlogApiException("Role Not Found : ".concat(AppConstants.ROLE_USER)));
                roleEntities.add(roleAdmin);
                roleEntities.add(roleUser);

                UserEntity adminUser = UserEntity.builder().name(AppConstants.ADMIN_NAME).email(AppConstants.ADMIN_EMAIL).password(passwordEncoder.encode(AppConstants.ADMIN_PASSWORD)).roles(roleEntities).build();

                userRepository.save(adminUser);
            }

            if (!userRepository.existsByName(AppConstants.USER_NAME) && !userRepository.existsByEmail(AppConstants.USER_EMAIL)) {

                Set<RoleEntity> roleEntities = new HashSet<>();
                RoleEntity roleUser = roleRepository.findByName(AppConstants.ROLE_USER).orElseThrow(() -> new BlogApiException("Role Not Found : ".concat(AppConstants.ROLE_USER)));
                roleEntities.add(roleUser);

                UserEntity normalUser = UserEntity.builder().name(AppConstants.USER_NAME).email(AppConstants.USER_EMAIL).password(passwordEncoder.encode(AppConstants.USER_PASSWORD)).roles(roleEntities).build();
                userRepository.save(normalUser);
            }

        }catch (Exception e) {

            log.error("Exception : " + e);
        }finally {

            log.info("Executing Pre-Scripts End");
        }
    }
}
