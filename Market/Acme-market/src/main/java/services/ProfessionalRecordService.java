package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProfessionalRecordRepository;
import domain.Curricula;
import domain.ProfessionalRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private Validator validator;
	
	//Simple CRUD methods -----
	
	public ProfessionalRecord create(){
		ProfessionalRecord res = new ProfessionalRecord();
					
		return res;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int Id){
		return professionalRecordRepository.findOne(Id);
	}
	
	public ProfessionalRecord save(ProfessionalRecord a){
		
		ProfessionalRecord saved = professionalRecordRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(ProfessionalRecord a){
		professionalRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<ProfessionalRecord> findProfessionalRecordsByCurricula(int curricula){
		return this.professionalRecordRepository.findProfessionalRecordsByCurricula(curricula);
	}
	
	public ProfessionalRecord reconstruct(ProfessionalRecord professional, BindingResult bindingResult) {
		ProfessionalRecord res = new ProfessionalRecord();
		
		if(professional.getId()==0){
			res = professional;
			Curricula c = curriculaService.findCurriculaByProvider(providerService.getPrincipal().getId());
			res.setCurricula(c);
		}else{
			ProfessionalRecord e = professionalRecordRepository.findOne(professional.getId());
			res=e;
			res.setCompanyName(professional.getCompanyName());
			res.setStartDate(professional.getStartDate());
			res.setEndDate(professional.getEndDate());
			res.setRole(professional.getRole());
			res.setAttachment(professional.getAttachment());
			res.setComments(professional.getComments());
		}

		validator.validate(professional, bindingResult);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getFieldErrors());
			throw new ValidationException();
		}

		return res;
	}

}