package sharecare.repository;

import org.springframework.data.repository.CrudRepository;
import sharecare.model.dto.PaymentConfirmation;

public interface PaymentConfirmationRepository extends CrudRepository<PaymentConfirmation, String> {
}
