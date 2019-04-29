package sharecare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharecare.model.Consultation;
import sharecare.model.Lesson;
import sharecare.model.User;
import sharecare.repository.LessonRepository;
import sharecare.service.LessonService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson createFromConsultation(Consultation consultation, User user, LocalDateTime time, Long duration) {
        return Lesson.builder()
                .title(consultation.getTitle())
                .description(consultation.getDescription())
                .language(consultation.getLanguage())
                .duration(duration)
                .price(consultation.getPrice())
                .time(time)
                .charityOrganization(consultation.getCharityOrganization())
                .charityPercentage(consultation.getCharityPercentage())
                .createdAt(LocalDateTime.now())
                .expert(consultation.getExpert())
                .user(user)
                .consultation(consultation)
                .build();
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void create(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void update(Lesson lesson) {
        lessonRepository.save(lesson);
    }

}
