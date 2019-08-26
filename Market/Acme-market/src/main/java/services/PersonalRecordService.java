package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalRecordRepository;
import domain.Curricula;
import domain.PersonalRecord;
import domain.Provider;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public PersonalRecord create(){
		PersonalRecord res = new PersonalRecord();
		Provider provider = providerService.getPrincipal();
		res.setFullName(provider.getName() + " "+ provider.getMiddleName() + " " +provider.getSurname());
		res.setEmail(provider.getEmail());
		res.setPhone(provider.getPhone());
		res.setPhoto(provider.getPhoto());
		return res;
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int Id){
		return personalRecordRepository.findOne(Id);
	}
	
	public PersonalRecord save(PersonalRecord a){
		
		PersonalRecord saved = personalRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(PersonalRecord a){
		personalRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	public PersonalRecord reconstruct(PersonalRecord personal, BindingResult bindingResult) {
		PersonalRecord res = personal;
		
		if(personal.getId()==0){
			Curricula c = new Curricula();
			c.setProvider(providerService.getPrincipal());
			Curricula saved = curriculaService.save(c);
			res.setCurricula(saved);
		}else{
			PersonalRecord p = personalRecordRepository.findOne(personal.getId());
			res=p;
			res.setFullName(personal.getFullName());
			res.setEmail(personal.getEmail());
			res.setLinkedInUrl(personal.getLinkedInUrl());
			res.setPhone(personal.getPhone());
			res.setPhoto(personal.getPhoto());
		}

		validator.validate(personal, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}
	
	public PersonalRecord findPersonalRecordByCurricula(int curricula){
		return this.personalRecordRepository.findPersonalRecordByCurricula(curricula);
	}

}