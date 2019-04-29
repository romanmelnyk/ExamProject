package sharecare.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharecare.model.Lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmation {
    @Id
    @Column(length = 100)
    String id;
    LocalDateTime createAt;
    @OneToOne
    Lesson lesson;
}
