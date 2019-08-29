package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Customer;


@Service
@Transactional
public class CommentService {

	//Managed Repository -----
	
	@Autowired
	private CommentRepository commentRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	
	public Comment create(){
		Comment res = new Comment();
		res.setMoment(new Date());
		return res;
	}
	
	public Collection<Comment> findAll(){
		return commentRepository.findAll();
	}
	
	public Comment findOne(int Id){
		return commentRepository.findOne(Id);
	}
	
	public Comment save(Comment a){
		
		Comment saved = commentRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Comment a){
		commentRepository.delete(a);
	}
	
	//Other business methods -----
	


}