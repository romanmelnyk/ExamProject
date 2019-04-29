package sharecare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharecare.model.embeded.TimeSlot;
import sharecare.model.embeded.Language;
import sharecare.model.embeded.Price;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @NotNull
    String title;
    String description;
    Language language;
    @NotNull
    @NotEmpty
    @ManyToMany (cascade = CascadeType.ALL)
    List<TimeSlot> timeSlots;
    @NotNull
    Price price;
    @ManyToOne
    @NotNull
    CharityOrganization charityOrganization;
    @Deprecated
    Double charityPercentage;
    LocalDateTime createdAt;
    @ManyToOne
    @NotNull
    User expert;
    @ManyToMany
    List<Category> categories;
}
