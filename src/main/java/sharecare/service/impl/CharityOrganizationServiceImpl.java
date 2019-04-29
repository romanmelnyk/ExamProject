package sharecare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.model.CharityOrganization;
import sharecare.model.dto.CharityStat;
import sharecare.repository.CharityOrganizationRepository;
import sharecare.service.CharityOrganizationService;

import java.util.List;
import java.util.Optional;

@Service
public class CharityOrganizationServiceImpl implements CharityOrganizationService {

    private final CharityOrganizationRepository charityOrganizationRepository;

    @Autowired
    public CharityOrganizationServiceImpl(CharityOrganizationRepository charityOrganizationRepository) {
        this.charityOrganizationRepository = charityOrganizationRepository;
    }

    @Override
    public List<CharityOrganization> getAll() {
        return charityOrganizationRepository.findAll();
    }

    @Override
    public Optional<CharityOrganization> getById(Long id) {
        return charityOrganizationRepository.findById(id);
    }

    @Override
    public List<CharityStat> getCharityStats() {
        return charityOrganizationRepository.getCharityStats();
    }

    @Override
    public Double getCharityTotal() {
        return charityOrganizationRepository.getCharityTotal();
    }

}
