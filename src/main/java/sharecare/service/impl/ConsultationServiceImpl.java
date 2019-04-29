package sharecare.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.model.Category;
import sharecare.model.Consultation;
import sharecare.model.embeded.Currency;
import sharecare.model.embeded.Language;
import sharecare.model.embeded.Role;
import sharecare.repository.ConsultationRepository;
import sharecare.repository.UserRepository;
import sharecare.service.ConsultationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, UserRepository userRepository) {
        this.consultationRepository = consultationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Consultation> getAll() {
        return consultationRepository.findAll();
    }

    @Override
    public List<Consultation> findByFilters(List<Category> categories, Language language, Currency currency, Double minPrice, Double maxPrice) {
        return consultationRepository.getByFilter(categories, language, currency, minPrice, maxPrice);
    }

    @Override
    public Optional<Consultation> getById(Long id) {
        return consultationRepository.findById(id);
    }

    @Override
    public Consultation create(Consultation consultation) {
        val newExpert = consultation.getExpert();

        val oldExpert = userRepository.getOne(newExpert.getId());
        if(newExpert.getCompany() != null && !newExpert.getCompany().isEmpty()) {
            oldExpert.setCompany(newExpert.getCompany());
        }
        if(newExpert.getPhoto() != null && !newExpert.getPhoto().isEmpty()) {
            oldExpert.setPhoto(newExpert.getPhoto());
        }
        if(newExpert.getPosition() != null && !newExpert.getPosition().isEmpty()) {
            oldExpert.setPosition(newExpert.getPosition());
        }

        if (!oldExpert.getRoles().contains(Role.EXPERT)) {
            oldExpert.getRoles().add(Role.EXPERT);
        }
        userRepository.saveAndFlush(oldExpert);

        consultation.setExpert(oldExpert);

        return consultationRepository.save(consultation);
    }

}
