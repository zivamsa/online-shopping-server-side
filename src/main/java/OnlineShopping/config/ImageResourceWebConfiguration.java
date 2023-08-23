package OnlineShopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageResourceWebConfiguration implements WebMvcConfigurer {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        String resourceLocation =  String.format("file:///%s/%s/", System.getProperty("user.dir"), uploadDirectory);
        registry
                .addResourceHandler("/image/**")
                .addResourceLocations(resourceLocation);
    }

}
