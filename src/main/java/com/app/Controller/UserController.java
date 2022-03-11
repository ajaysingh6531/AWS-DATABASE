package com.app.Controller;

import java.util.List;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.Entity.User;
import com.app.Exception.ResourseNotFoundException;
import com.app.Repository.UserRepository;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	//get all users 
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRepo.findAll();
	}
	
	//get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User not found with id:"+ userId));
	}
	//create user
	@PostMapping
	public User createUser(@RequestBody User u) {
		return this.userRepo.save(u);
	}
	//update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User u,@PathVariable("id") long UserId) {
		User existing=this.userRepo.findById(UserId).orElseThrow(() -> new ResourseNotFoundException("User not found with id:"+ UserId));
		existing.setFirstName(u.getFirstName());
		existing.setLastName(u.getLastName());
		existing.setEmail(u.getEmail());
		return this.userRepo.save(existing);
	}
	
	//delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId){
		User existing=this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User not found with id:"+ userId));
		this.userRepo.delete(existing);
		return ResponseEntity.ok().build();
	}
	
	
}
