package sharecare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharecare.model.embeded.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String title;
    String description;
    Language language;
    Long duration;
    Price price;
    LocalDateTime time;
    @ManyToOne
    CharityOrganization charityOrganization;
    Double charityPercentage;
    LocalDateTime createdAt;
    @ManyToOne
    User expert;
    @ManyToOne
    User user;
    Payment payment;
    CommunicationChannel chat;
    CommunicationChannel videoChat;
    Feedback feedback;
    @ManyToOne
    Consultation consultation;
}
