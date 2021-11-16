package by.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "springdoc.info")
@OpenAPIDefinition(info = @Info(title = "${springdoc.info.title}",
        description = "${springdoc.info.description}",
        version = "${springdoc.info.version}",
        termsOfService = "${springdoc.info.termsOfService}",
        license = @License(name = "${springdoc.info.license.name}",
                url = "${springdoc.info.license.url}"),
        contact = @Contact(
                name = "${springdoc.info.contact.name}",
                email = "${springdoc.info.contact.email}",
                url = "${springdoc.info.contact.url}"
        )
))
public class SpringDocConfig {

}
