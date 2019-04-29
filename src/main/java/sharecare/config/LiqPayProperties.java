package sharecare.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "liqpay")
@Configuration
public class LiqPayProperties {
    String privateKey;
    String publicKey;
}
