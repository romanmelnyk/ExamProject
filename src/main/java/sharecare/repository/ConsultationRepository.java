package sharecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sharecare.model.Category;
import sharecare.model.Consultation;
import sharecare.model.embeded.Currency;
import sharecare.model.embeded.Language;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query( "from Consultation c " +
            "where (:language is null or language = :language) " +
            "  and not exists (select searchCat from Category searchCat where searchCat in (:categories) and searchCat not in (select consCat from c.categories consCat)) " +
            "  and (:currency is null or (c.price.currency = :currency and c.price.amount between :minPrice and :maxPrice))"
    )
    List<Consultation> getByFilter(@Param("categories") List<Category> categories,
                                   @Param("language") Language language,
                                   @Param("currency") Currency currency,
                                   @Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice);
}
