
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;
import domain.Teacher;

@Service
@Transactional
public class EducationRecordService {

	// Managed Repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services

	@Autowired
	private TeacherService				teacherService;


	// Constructors

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<EducationRecord> findByPrincipal() {
		Collection<EducationRecord> result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCurriculum().getEducationRecord();

		Assert.notNull(result);

		return result;
	}

	public EducationRecord create() {
		EducationRecord result;
		Teacher principal;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		result = new EducationRecord();
		result.setComments(new ArrayList<String>());
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		EducationRecord result;
		Teacher principal;
		List<EducationRecord> educationsRecords;

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.isTrue(educationRecord.getEndDate().after(educationRecord.getStartDate()));
		educationsRecords = principal.getCurriculum().getEducationRecord();
		result = this.educationRecordRepository.save(educationRecord);
		Assert.notNull(result);
		if (educationRecord.getId() == 0) {
			educationsRecords.add(result);
			principal.getCurriculum().setEducationRecord(educationsRecords);
		}
		return result;

	}

	public void delete(final EducationRecord educationRecord) {
		Teacher principal;
		List<EducationRecord> listEducationRecord;

		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		principal = this.teacherService.findByPrincipal();
		Assert.notNull(principal);

		listEducationRecord = principal.getCurriculum().getEducationRecord();

		this.educationRecordRepository.delete(educationRecord);
		listEducationRecord.remove(educationRecord);

		principal.getCurriculum().setEducationRecord(listEducationRecord);

	}

	public EducationRecord findOne(final int id) {
		EducationRecord result;
		result = this.educationRecordRepository.findOne(id);
		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;
		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public void flush() {
		this.educationRecordRepository.flush();
	}

	// Other business methods

}
