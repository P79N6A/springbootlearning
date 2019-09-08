package configuration.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("people")
@ConfigurationProperties(prefix = "people")
public class People {
//    @Value("${people.name}")
    private String name;
//    @Value("${people.age}")
    private int age;
}
