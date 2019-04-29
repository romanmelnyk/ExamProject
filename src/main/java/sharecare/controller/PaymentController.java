package sharecare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sharecare.config.LiqPayProperties;
import sharecare.model.dto.LiqpayCallbackData;
import sharecare.service.ConsultationService;
import sharecare.service.PaymentConfirmationService;
import sharecare.service.PaymentService;
import sharecare.service.UserService;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

import static com.liqpay.LiqPayUtil.base64_encode;
import static com.liqpay.LiqPayUtil.sha1;
import static java.time.temporal.ChronoUnit.MINUTES;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private static final int PAYMENT_TIMEOUT_MINS = 60;

    private final PaymentService paymentService;
    private final ConsultationService consultationService;
    private final UserService userService;
    private final PaymentConfirmationService paymentConfirmationService;
    private final LiqPayProperties liqPayProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public PaymentController(PaymentService paymentService, ConsultationService consultationService, UserService userService, PaymentConfirmationService paymentConfirmationService, LiqPayProperties liqPayProperties, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.consultationService = consultationService;
        this.userService = userService;
        this.paymentConfirmationService = paymentConfirmationService;
        this.liqPayProperties = liqPayProperties;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/button", produces = "text/html")
    public String getPaymentButton(@RequestParam Long consultationId,
                                   @RequestParam Long userId,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
                                   @RequestParam Long duration) {
        val consultation = consultationService.getById(consultationId).orElseGet(null);
        val user = userService.getById(userId).orElseGet(null);
        return paymentService.generatePaymentButton(consultation, user, time, duration);
    }

    @PostMapping("/confirmation/{key}")
    public void confirmPayment(@PathVariable String key, @RequestParam Map<String, String> body) {
        val paymentConfirmationOrNot = paymentConfirmationService.getByKey(key);
        String signature = URLDecoder.decode(body.get("signature"));
        String data = URLDecoder.decode(body.get("data"));

        if (paymentConfirmationOrNot.isPresent()) {
            val paymentConfirmation = paymentConfirmationOrNot.get();

            if (!timeoutPassedFrom(paymentConfirmation.getCreateAt()) && bodyIsValid(signature, data)) {
                paymentService.paymentConfirmationCallback(parseCallbackData(data), paymentConfirmation);
            }
        }
    }

    private boolean bodyIsValid(String signagure, String data) {
        return true;
//        return signagure.equals(base64_encode(sha1(
//                liqPayProperties.getPublicKey() + data + liqPayProperties.getPublicKey()
//                )
//            )
//        );
    }

    private LiqpayCallbackData parseCallbackData(String data) {
        try {
            return objectMapper.readValue(new String(Base64.getDecoder().decode(data)), LiqpayCallbackData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean timeoutPassedFrom(LocalDateTime createdAt) {
        return MINUTES.between(createdAt, LocalDateTime.now()) > PAYMENT_TIMEOUT_MINS;
    }

}
