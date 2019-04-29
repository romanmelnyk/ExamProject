package sharecare.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.model.Lesson;
import sharecare.model.dto.PaymentConfirmation;
import sharecare.repository.PaymentConfirmationRepository;
import sharecare.service.PaymentConfirmationService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentConfirmationServiceImpl implements PaymentConfirmationService {

    private final PaymentConfirmationRepository paymentConfirmationRepository;

    @Autowired
    public PaymentConfirmationServiceImpl(PaymentConfirmationRepository paymentConfirmationRepository) {
        this.paymentConfirmationRepository = paymentConfirmationRepository;
    }

    @Override
    public String create(Lesson lesson) {
        val paymentConfirmation = PaymentConfirmation.builder()
                .id(UUID.randomUUID().toString())
                .createAt(LocalDateTime.now())
                .lesson(lesson)
                .build();

        return paymentConfirmationRepository.save(paymentConfirmation).getId();
    }

    @Override
    public Optional<PaymentConfirmation> getByKey(String key) {
        return paymentConfirmationRepository.findById(key);
    }

    @Override
    public void delete(String key) {
        paymentConfirmationRepository.deleteById(key);
    }
}
