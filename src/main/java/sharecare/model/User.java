package sharecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sharecare.model.embeded.LinkedInAccount;
import sharecare.model.embeded.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @NotNull
    String email;
    @NotNull
    @JsonProperty(access = WRITE_ONLY)
    String password;
    @NotNull
    @ElementCollection(targetClass = Role.class)
    List<Role> roles;
    String name;
    String photo;
    String description;
    @ManyToMany
    @Deprecated
    List<Category> fieldsOfInterest;
    @ManyToMany
    @Deprecated
    List<Category> fieldsOfExpertise;
    String company;
    String position;
    @Deprecated
    LinkedInAccount linkedInAccount;
}
