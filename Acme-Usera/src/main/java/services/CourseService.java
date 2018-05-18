package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CourseRepository;
import domain.Admin;
import domain.Advertisement;
import domain.Lesson;
import domain.Student;
import domain.Subscription;
import domain.Teacher;

import domain.Course;


@Service
@Transactional
public class CourseService {

	// Managed Repository
	@Autowired
	private CourseRepository			courseRepository;

	// Supporting services
	@Autowired
	private TeacherService				teacherService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private SubscriptionService			subscriptionService;
	
	@Autowired
	private StudentService	studentService;

	// Constructors

	public CourseService() {
		super();
	}

	// Simple CRUD methods

	
	public Course create() {
		Teacher principal;
		Course course = new Course();

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		course.setCreationDate(new Date(System.currentTimeMillis()));
		course.setIsClosed(false);
		course.setAdvertisements(new ArrayList<Advertisement>());
		course.setLessons(new ArrayList<Lesson>());
		course.setSubscriptions(new ArrayList<Subscription>());

		return course;
	}


	public Collection<Course> findAll() {
		final Collection<Course> result = this.courseRepository.findAll();
		
		
		return result;
	}

	// Other business methods

	public void deleteByAdmin(final Course course) {
		Admin principal;

		Assert.notNull(course);
		Assert.isTrue(course.getId() != 0);
		principal = this.adminService.findByPrincipal();
		Assert.notNull(principal);

		final Collection<Advertisement> adversToRemove = course.getAdvertisements();

		final Collection<Subscription> subscriptionsToRemove = course.getSubscriptions();
		Collection<Teacher> teachers = course.getTeachers();


		for (final Advertisement ad : adversToRemove){
			Collection<Course> toUpdate = ad.getCourses();
			Collection<Course> updated = new ArrayList<Course>(toUpdate);

			updated.remove(course);
			ad.setCourses(updated);
			
		}			
		for (final Subscription s : subscriptionsToRemove)
			this.subscriptionService.delete(s);


		

		for (final Teacher t : teachers) {
			final Collection<Course> courses = t.getCoursesJoined();
			final Collection<Course> updated2 = new ArrayList<Course>(courses);
			updated2.remove(course);
			t.setCoursesJoined(updated2);
		}
		
		Teacher teacher = course.getCreator();
		final Collection<Course> courses = teacher.getCoursesCreated();
		final Collection<Course> updated2 = new ArrayList<Course>(courses);
		teacher.setCoursesCreated(updated2);

		this.courseRepository.delete(course);

	}
	// Teachers must be able to create Courses
	public Course save(final Course courseToSave) {
		Teacher principal;
		Course result;
		Assert.notNull(courseToSave);

		principal = this.teacherService.findByPrincipal();

		Assert.notNull(principal);
		
		if (courseToSave.getId() != 0){
		Assert.isTrue(principal.getCoursesCreated().contains(courseToSave));
		}
		
	
		result = this.courseRepository.save(courseToSave);
	

		if (courseToSave.getId() == 0) {
			final Collection<Teacher> teachers = new ArrayList<Teacher>();
			teachers.add(principal);
			result.setCreator(principal);
			result.setTeachers(teachers);

			final Collection<Course> joined = principal.getCoursesJoined();
			joined.add(result);
			principal.setCoursesJoined(joined);

		}

		
		
		return result;
	}
	public Collection<Course> findJoinedByTeacherId(final int id) {
		Teacher principal;
		Collection<Course> result;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		result = this.courseRepository.selectJoinedByTeacherId(id);

		Assert.notNull(result);

		return result;

	}


	public Course findOne(final int CourseId) {


		Course result = this.courseRepository.findOne(CourseId);
		Assert.notNull(result);

		return result;

	}



	public Teacher join(final Course course, final Teacher teacher) {
		Teacher result;
		Teacher principal = this.teacherService.findByPrincipal();
		Assert.isTrue(!principal.getCoursesJoined().contains(course)&& !principal.getCoursesCreated().contains(course));
		if (!course.getTeachers().contains(teacher)) {
			course.getTeachers().add(teacher);
			teacher.getCoursesJoined().add(course);
		}
		result = teacher;
		return result;
	}

	public Teacher cancelJoin(final Course course, final Teacher teacher) {
		Teacher result;
		Teacher principal = this.teacherService.findByPrincipal();
		Assert.isTrue(!principal.getCoursesCreated().contains(course)&& principal.getCoursesJoined().contains(course));
		
		if (course.getTeachers().contains(teacher)) {
			course.getTeachers().remove(teacher);
			teacher.getCoursesJoined().remove(course);
		}
		result = teacher;
		return result;
	}
	
	
	
	public Collection<Course> findCourseByCategory(Integer categoryId){
		Collection<Course> result;
		
		result = this.courseRepository.findCourseByCategory(categoryId);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Course> selectCoursesSubscriptedByUser(int id){
		Student principal = this.studentService.findByPrincipal();
		Assert.notNull(principal);
		Collection<Course> res = this.courseRepository.selectCoursesSubscriptedByUser(id);
		return res;
	}
	public void flush(){
		this.courseRepository.flush();
	}
}