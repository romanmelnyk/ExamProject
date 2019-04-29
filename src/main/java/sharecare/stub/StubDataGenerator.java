package sharecare.stub;

import com.github.javafaker.Faker;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sharecare.model.*;
import sharecare.model.User.UserBuilder;
import sharecare.model.embeded.Currency;
import sharecare.model.embeded.Language;
import sharecare.model.embeded.Price;
import sharecare.repository.*;
import sharecare.service.LessonService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static sharecare.model.embeded.Role.EXPERT;
import static sharecare.model.embeded.Role.USER;

@Component
public class StubDataGenerator implements CommandLineRunner  {

    private static final Category[] CATEGORIES = {
            new Category("IT", "This is all about IT, programming and stuff like that. Cool!"),
            new Category("Traveling", "Well, obvious"),
            new Category("Business", null),
            new Category("Self-dev", "Yoga, meditation, reading, self education"),
            new Category("Humor", null),
            new Category("Animals", null)
    };

    private static final int USERS_NUM = 10;
    private static final int CHARITIES_NUM = 10;
    private static final int CONSULTATIONS_NUM = 10;
    private static final int LESSONS_NUM = 10;

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CharityOrganizationRepository charityOrganizationRepository;
    private final ConsultationRepository consultationRepository;
    private final LessonRepository lessonRepository;
    private final LessonService lessonService;

    private final Faker faker = new Faker();

    @Autowired
    public StubDataGenerator(CategoryRepository categoryRepository, UserRepository userRepository, CharityOrganizationRepository charityOrganizationRepository, ConsultationRepository consultationRepository, LessonRepository lessonRepository, LessonService lessonService) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.charityOrganizationRepository = charityOrganizationRepository;
        this.consultationRepository = consultationRepository;
        this.lessonRepository = lessonRepository;
        this.lessonService = lessonService;
    }

    @Override
    public void run(String... args) throws Exception {
//        val categories = createStubCategories();
//        val charities = createStubCharities();
//        val allUsers = createStubUsers();
//
//        val experts = allUsers.stream().filter(user -> user.getRoles().contains(EXPERT)).collect(toList());
//        val users = allUsers.stream().filter(user -> user.getRoles().contains(USER)).collect(toList());
//
//        val consultations = createStubConsultations(experts, charities, categories);
//        val lessons = createStubLesson(consultations, users);
    }

    private List<Category> createStubCategories() {
        return categoryRepository.saveAll(Arrays.asList(CATEGORIES));
    }

    private List<CharityOrganization> createStubCharities() {
        return charityOrganizationRepository.saveAll(
                IntStream.range(0, CHARITIES_NUM)
                    .mapToObj(i -> CharityOrganization.builder()
                        .name(faker.company().name())
                        .description(safeVarchar(faker.lorem().paragraph()))
                        .build())
                    .collect(toList())
        );
    }

    private List<User> createStubUsers() {
        return userRepository.saveAll(IntStream.range(0, USERS_NUM)
                .mapToObj(i -> userRoleInfo(User.builder()
                        .email(faker.internet().emailAddress())
                        .password(faker.internet().password())
                        .photo(faker.internet().avatar())
                        .name(faker.name().name())
                        .description(safeVarchar(faker.lorem().paragraph())))
                        .build()
                )
                .collect(toList()));
    }

    private UserBuilder userRoleInfo(UserBuilder userBuilder) {
        double randomValue = Math.random();
        if (randomValue > 0.66) {
            return  userInfo(userBuilder.roles(singletonList(USER)));
        } else if (randomValue > 0.33) {
            return expertInfo(userBuilder.roles(singletonList(EXPERT)));
        }

        return userInfo(expertInfo(userBuilder.roles(Arrays.asList(USER, EXPERT))));
    }

    private UserBuilder userInfo(UserBuilder userBuilder) {
        return userBuilder
                .fieldsOfExpertise(emptyList()); // TODO implement
    }

    private UserBuilder expertInfo(UserBuilder userBuilder) {
        return userBuilder
                .fieldsOfExpertise(emptyList()) // TODO implement
                .company(faker.company().name())
                .position(faker.job().position());
    }

    private List<Consultation> createStubConsultations(List<User> experts, List<CharityOrganization> charities, List<Category> categories) {
        return consultationRepository.saveAll(
                IntStream.range(0, CONSULTATIONS_NUM)
                        .mapToObj(i -> Consultation.builder()
                                .title(String.join(" ", faker.lorem().words(5)))
                                .description(safeVarchar(faker.lorem().paragraph()))
                                .language(Language.values()[faker.random().nextInt(Language.values().length)])
                                .timeSlots(emptyList()) // TODO implement
                                .price(Price.builder()
                                        .amount(10.0 * faker.random().nextInt(1, 100))
                                        .currency(Currency.values()[faker.random().nextInt(Currency.values().length)])
                                        .build())
                                .charityOrganization(charities.get(faker.random().nextInt(charities.size())))
                                .charityPercentage(40.0 + 5 * faker.random().nextInt(8))
                                .createdAt(LocalDateTime.ofInstant(faker.date().past(365, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                                .expert(experts.get(faker.random().nextInt(experts.size())))
                                .categories(categories.stream().collect(toShuffledList()).subList(0, faker.random().nextInt(categories.size())))
                                .build())
                        .collect(toList())
        );
    }

    private static <T> Collector<T, ?, List<T>> toShuffledList() {
        return Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
            Collections.shuffle(list);
            return list;
        });
    }

    private static String safeVarchar(String str) {
        return str.substring(0, Math.min(str.length(), 255));
    }

    private List<Lesson> createStubLesson(List<Consultation> consultations, List<User> users) {
        return lessonRepository.saveAll(
                consultations.stream()
                        .map(c -> lessonService.createFromConsultation(
                                c,
                                users.get(faker.random().nextInt(users.size())),
                                toLdt(faker.date().between(toDate(c.getCreatedAt()), new Date())),
                                (long) faker.random().nextInt(1, 3)
                        ))
                        .collect(toList())
        );
    }

    private Date toDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLdt(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
