package sharecare.service;

import sharecare.model.Category;
import sharecare.model.Consultation;
import sharecare.model.embeded.Currency;
import sharecare.model.embeded.Language;

import java.util.List;
import java.util.Optional;

public interface ConsultationService {

    List<Consultation> getAll();

    List<Consultation> findByFilters(List<Category> categories, Language language, Currency currency, Double minPrice, Double maxPrice);

    Optional<Consultation> getById(Long id);

    Consultation create(Consultation consultation);

}
