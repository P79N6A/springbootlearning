package configuration.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("student")
@ConfigurationProperties(prefix = "student")
public class Student {
    private String school;
}
