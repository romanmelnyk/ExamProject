package sharecare.model.dto;

import lombok.Data;

@Data
public class LiqpayPaymentCallback {
    String signature;
    String data;
}
