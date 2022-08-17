package com.example.user.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        com.saltfish.example.aseptcut.UploadAsept.class
})
public class FastConfig {
}
