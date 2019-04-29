package sharecare.config;

import com.liqpay.LiqPay;
import com.liqpay.LiqPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiqPayConfiguration {

    @Bean
    @Autowired
    public LiqPayApi liqPayApi(LiqPayProperties properties) {
        return new LiqPay(properties.getPublicKey(), properties.getPrivateKey());
    }

}
