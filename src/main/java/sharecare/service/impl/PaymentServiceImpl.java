package sharecare.service.impl;

import com.google.common.collect.ImmutableMap;
import com.liqpay.LiqPayApi;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.config.LiqPayProperties;
import sharecare.config.RuntimeProperties;
import sharecare.model.Consultation;
import sharecare.model.Lesson;
import sharecare.model.User;
import sharecare.model.dto.LiqpayCallbackData;
import sharecare.model.dto.PaymentConfirmation;
import sharecare.model.embeded.Payment;
import sharecare.model.embeded.PaymentStatus;
import sharecare.service.LessonService;
import sharecare.service.PaymentConfirmationService;
import sharecare.service.PaymentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static sharecare.model.embeded.PaymentStatus.FAILED;
import static sharecare.model.embeded.PaymentStatus.SUCCESSFUL;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final LiqPayApi liqPayApi;
    private final LiqPayProperties liqPayProperties;
    private final PaymentConfirmationService paymentConfirmationService;
    private final LessonService lessonService;
    private final RuntimeProperties runtimeProperties;

    @Autowired
    public PaymentServiceImpl(LiqPayApi liqPayApi, LiqPayProperties liqPayProperties, PaymentConfirmationService paymentConfirmationService, LessonService lessonService, RuntimeProperties runtimeProperties) {
        this.liqPayApi = liqPayApi;
        this.liqPayProperties = liqPayProperties;
        this.paymentConfirmationService = paymentConfirmationService;
        this.lessonService = lessonService;
        this.runtimeProperties = runtimeProperties;
    }

    @Override
    public String generatePaymentButton(Consultation consultation, User user, LocalDateTime time, Long duration) {
        val lesson = lessonService.createFromConsultation(consultation, user, time, duration);
        lessonService.create(lesson);

        val data = ImmutableMap.<String, String>builder()
            .put("version", "3")
            .put("public_key", liqPayProperties.getPublicKey())
            .put("action", "pay")
            .put("amount", consultation.getPrice().getAmount().toString())
            .put("currency", consultation.getPrice().getCurrency().toString())
            .put("description", "")
            .put("order_id", UUID.randomUUID().toString())
            .put("language", "en")
            .put("result_url", "/")
            .put("server_url", generateConfirmationUrl(lesson))
            .put("sandbox", runtimeProperties.getIsLiqpayTestEnv() ? "1" : "0")
            .put("letter_of_credit", "1")
            .put("letter_of_credit_date", calculateAndFormatExpirationTime(time, duration))
            .build();

        return liqPayApi.cnb_form(data);
    }

    @Override
    public void paymentConfirmationCallback(LiqpayCallbackData data, PaymentConfirmation paymentConfirmation) {
        val lesson = paymentConfirmation.getLesson();
        paymentConfirmationService.delete(paymentConfirmation.getId());

        lesson.setPayment(Payment.builder()
                .status(mapStatus(data.getStatus()))
                .build()
        );

        lessonService.update(lesson);
    }

    private static PaymentStatus mapStatus(String status) {
        switch (status.toLowerCase()) {
            case "sandbox": case "success": return SUCCESSFUL;
            case "error": case "failure": return FAILED;
        }
        return null;
    }

    private String generateConfirmationUrl(Lesson lesson) {
        String lessonKey = paymentConfirmationService.create(lesson);

        return "/payment/confirmation/" + lessonKey;
    }

    private String calculateAndFormatExpirationTime(LocalDateTime time, Long duration) {
        return time.plus(duration, ChronoUnit.HOURS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
