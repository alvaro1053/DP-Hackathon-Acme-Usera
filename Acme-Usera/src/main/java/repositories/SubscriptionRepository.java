
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {


	@Query("select s from Subscription s where s.customer.id =?1 and s.course.id=?2")
	Subscription findByStudentAndCourse(int customerId, int courseId);
	
}
