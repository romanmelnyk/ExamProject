package sharecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sharecare.model.CharityOrganization;
import sharecare.model.dto.CharityStat;

import java.util.List;

public interface CharityOrganizationRepository extends JpaRepository<CharityOrganization, Long> {

    @Query("select new sharecare.model.dto.CharityStat(ch , sum(coalesce(l.price.amount, 0))*0.8) " +
            "from CharityOrganization ch " +
            "left join Lesson l " +
            "on l.charityOrganization = ch and l.payment is not null and l.payment.status = sharecare.model.embeded.PaymentStatus.SUCCESSFUL " +
            "group by ch.id ")
    List<CharityStat> getCharityStats();

    @Query("select sum(coalesce(l.price.amount, 0))*0.8 " +
            "from CharityOrganization ch " +
            "left join Lesson l " +
            "on l.charityOrganization = ch and l.payment is not null and l.payment.status = sharecare.model.embeded.PaymentStatus.SUCCESSFUL")
    Double getCharityTotal();
}
