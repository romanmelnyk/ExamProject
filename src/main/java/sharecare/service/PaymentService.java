package sharecare.service;

import sharecare.model.Consultation;
import sharecare.model.User;
import sharecare.model.dto.LiqpayCallbackData;
import sharecare.model.dto.LiqpayPayment;
import sharecare.model.dto.LiqpayPaymentResult;
import sharecare.model.dto.PaymentConfirmation;

import java.time.LocalDateTime;

public interface PaymentService {

    String generatePaymentButton(Consultation consultation, User user, LocalDateTime time, Long duration);

    void paymentConfirmationCallback(LiqpayCallbackData data, PaymentConfirmation paymentConfirmation);

}
