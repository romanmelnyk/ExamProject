package sharecare.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import sharecare.model.CharityOrganization;

@Data
@AllArgsConstructor
public class CharityStat {
    CharityOrganization charity;
    Double total;
}
