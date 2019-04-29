package sharecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sharecare.model.Category;
import sharecare.model.Consultation;
import sharecare.model.embeded.Currency;
import sharecare.model.embeded.Language;
import sharecare.service.ConsultationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public List<Consultation> getByFilters(@RequestParam(required = false) List<Long> category,
                                           @RequestParam(required = false) Language language,
                                           @RequestParam(required = false) Currency currency,
                                           @RequestParam(required = false) Double minPrice,
                                           @RequestParam(required = false) Double maxPrice) {
        return consultationService.findByFilters(initCategories(category), language, currency, minPrice, maxPrice);
    }

    @GetMapping("/{id}")
    public Optional<Consultation> getById(@PathVariable Long id) {
        return consultationService.getById(id);
    }

    @PostMapping
    public Consultation createNew(@Valid @RequestBody Consultation consultation) {
        return consultationService.create(consultation);
    }

    private static List<Category> initCategories(List<Long> ids) {
        return ids == null ? null : ids.stream().map(id -> Category.builder().id(id).build()).collect(toList());
    }

}
