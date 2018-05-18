
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("select t.coursesJoined from Teacher t where t.id = ?1")
	public Collection<Course> selectJoinedByTeacherId(int id);
	
	@Query("select distinct co from Student s join s.subscriptions sub join sub.course co where s.id = ?1")
	public Collection<Course> selectCoursesSubscriptedByUser(int id);
	
	@Query("select distinct co from Category c join c.courses co where c.id=?1")	
    public Collection<Course> findCourseByCategory(Integer categoryId);
	


}