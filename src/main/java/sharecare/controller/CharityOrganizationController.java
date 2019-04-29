package sharecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharecare.model.CharityOrganization;
import sharecare.model.dto.CharityStat;
import sharecare.service.CharityOrganizationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("charities")
public class CharityOrganizationController {

    private final CharityOrganizationService charityOrganizationService;

    @Autowired
    public CharityOrganizationController(CharityOrganizationService charityOrganizationService) {
        this.charityOrganizationService = charityOrganizationService;
    }

    @GetMapping
    public List<CharityOrganization> getAll() {
        return charityOrganizationService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CharityOrganization> getById(@PathVariable Long id) {
        return charityOrganizationService.getById(id);
    }

    @GetMapping("/stats")
    public List<CharityStat> getCharityStats() {
        return charityOrganizationService.getCharityStats();
    }

    @GetMapping("/stats/total")
    public Double getCharityTotal() {
        return charityOrganizationService.getCharityTotal();
    }

}
