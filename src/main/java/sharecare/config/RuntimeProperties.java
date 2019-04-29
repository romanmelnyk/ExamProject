package sharecare.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "runtime")
@Data
public class RuntimeProperties {
    Boolean isLiqpayTestEnv = true;
}
