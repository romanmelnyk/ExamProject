package sharecare.model.dto;

import lombok.Data;
import sharecare.model.embeded.Currency;

import java.time.ZonedDateTime;

@Data
public class LiqpayPayment {
    Double amount;
    Currency currency;
    String description;
    String orderId;
    ZonedDateTime expirationDate;
    String resultUrl;
    String serverUrl;
}
