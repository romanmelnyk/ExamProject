package sharecare.service;

import sharecare.model.Consultation;
import sharecare.model.Lesson;
import sharecare.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LessonService {

    List<Lesson> getAll();

    Lesson createFromConsultation(Consultation consultation, User user, LocalDateTime time, Long duration);

    Optional<Lesson> getById(Long id);

    void create(Lesson lesson);

    void update(Lesson lesson);

}
