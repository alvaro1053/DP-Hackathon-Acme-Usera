
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Certification;
import domain.MailMessage;
import domain.Question;
import domain.Student;
import domain.Subscription;
import domain.Tutorial;
import forms.ActorForm;

@Service
@Transactional
public class StudentService {

	// Managed Repository
	@Autowired
	private StudentRepository	studentRepository;
	
	@Autowired
	private FolderService folderService;

	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public StudentService() {
		super();
	}

	// Simple CRUD methods
	public Student create() {
		Student result;

		result = new Student();
		result.setScore(0);
		result.setSubscriptions(new ArrayList<Subscription>());
		result.setTutorials(new ArrayList<Tutorial>());
		result.setCertifications(new ArrayList<Certification>());
		result.setQuestions(new ArrayList<Question>());
		result.setReceivedMessages(new ArrayList<MailMessage>());
		result.setSentMessages(new ArrayList<MailMessage>());
		result.setFolders(this.folderService.createSystemFolders());
		
		return result;
	}

	public Student save(final Student student) {
		Student saved;
		Assert.notNull(student);

		if (student.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			student.getUserAccount().setPassword(passwordEncoder.encodePassword(student.getUserAccount().getPassword(), null));
		}

		saved = this.studentRepository.save(student);

		//TEST ASSERT - Testing if the user is in the system after saving him/her
		Assert.isTrue(this.studentRepository.findAll().contains(saved));
		//TEST ASSERT =========================================
		return saved;
	}

	public Student findOne(final int studentId) {
		Student result;
		result = this.studentRepository.findOne(studentId);
		return result;
	}

	public Collection<Student> findAll() {
		Collection<Student> result;
		result = this.studentRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public Student findByPrincipal() {
		Student result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Student findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Student result;
		result = this.studentRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Student reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Student student = this.create();
		student.setName(actorForm.getName());
		student.setSurname(actorForm.getSurname());
		student.setEmail(actorForm.getEmail());
		student.setId(actorForm.getId());
		student.setAddress(actorForm.getAddress());
		student.setVersion(actorForm.getVersion());
		student.setPhone(actorForm.getPhone());
		student.setUserAccount(actorForm.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("TEACHER");
		authorities.add(auth);
		student.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return student;
	}

	public void flush() {
		this.studentRepository.flush();
	}

}