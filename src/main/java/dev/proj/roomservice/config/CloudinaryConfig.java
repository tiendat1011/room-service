package dev.proj.roomservice.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${env.cloudinary.url}")
    private String cloudUrl;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(cloudUrl);
    }
}