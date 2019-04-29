package sharecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharecare.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
