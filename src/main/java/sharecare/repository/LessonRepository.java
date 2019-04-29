package sharecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sharecare.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
