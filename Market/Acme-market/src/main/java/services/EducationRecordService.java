package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationRecordRepository;
import domain.Curricula;
import domain.EducationRecord;
import domain.PersonalRecord;


@Service
@Transactional
public class EducationRecordService {

	//Managed Repository -----
	
	@Autowired
	private EducationRecordRepository educationRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public EducationRecord create(){
		EducationRecord res = new EducationRecord();
					
		return res;
	}
	
	public Collection<EducationRecord> findAll(){
		return educationRecordRepository.findAll();
	}
	
	public EducationRecord findOne(int Id){
		return educationRecordRepository.findOne(Id);
	}
	
	public EducationRecord save(EducationRecord a){
		
		EducationRecord saved = educationRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(EducationRecord a){
		educationRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<EducationRecord> findEducationRecordsByCurricula(int curricula){
		return this.educationRecordRepository.findEducationRecordsByCurricula(curricula);
	}
	
	public EducationRecord reconstruct(EducationRecord education, BindingResult bindingResult) {
		EducationRecord res = new EducationRecord();
		
		if(education.getId()==0){
			res = education;
			Curricula c = curriculaService.findCurriculaByProvider(providerService.getPrincipal().getId());
			res.setCurricula(c);
		}else{
			EducationRecord e = educationRecordRepository.findOne(education.getId());
			res=e;
			res.setDiplomaTitle(education.getDiplomaTitle());
			res.setStartDate(education.getStartDate());
			res.setEndDate(education.getEndDate());
			res.setInstitution(education.getInstitution());
			res.setAttachment(education.getAttachment());
			res.setComments(education.getComments());
		}

		validator.validate(res, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}

}