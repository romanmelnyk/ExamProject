package sharecare.service;

import sharecare.model.CharityOrganization;
import sharecare.model.dto.CharityStat;

import java.util.List;
import java.util.Optional;

public interface CharityOrganizationService {

    List<CharityOrganization> getAll();

    Optional<CharityOrganization> getById(Long id);

    List<CharityStat> getCharityStats();

    Double getCharityTotal();

}
