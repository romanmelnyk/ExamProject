package sharecare.service;

import sharecare.model.Lesson;
import sharecare.model.dto.PaymentConfirmation;

import java.util.Optional;

public interface PaymentConfirmationService {

    String create(Lesson lesson);

    Optional<PaymentConfirmation> getByKey(String key);

    void delete(String key);

}
