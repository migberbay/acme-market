package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public PersonalRecord create(){
		PersonalRecord res = new PersonalRecord();
					
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
	
	public PersonalRecord findPersonalRecordByCurricula(int curricula){
		return this.personalRecordRepository.findPersonalRecordByCurricula(curricula);
	}

}