package com.crud.simple.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.simple.dto.UserPageDto;
import com.crud.simple.exception.ResourceNotFoundException;
import com.crud.simple.exception.reqValidationException;
import com.crud.simple.model.User;
import com.crud.simple.repository.UserRepository;
import com.crud.simple.model.UserData;

import org.apache.commons.lang3.math.NumberUtils;

@RestController
@RequestMapping("/v1")
public class UserController{
	
	@Autowired
	private UserRepository user_repo;
	
	@GetMapping("/users/all")
	public List <User> getAllUsers(){
		return user_repo.findAll();
	}

	@GetMapping("/users")
	public UserPageDto getUsersByPage(@RequestParam(name = "page", defaultValue = "0") int page) throws ResourceNotFoundException {
		final int pageSize = 2;
		PageRequest pageWith2Elements = PageRequest.of(page,pageSize, Sort.by("id").ascending());
		Page <User> usersByPage = user_repo.findAll(pageWith2Elements);
		final int page_ends_at = usersByPage.getTotalPages();
		if(page < page_ends_at) {
			UserPageDto E2D = new UserPageDto(usersByPage.getContent(), usersByPage.isFirst(), usersByPage.isLast(), usersByPage.getNumber());
			return E2D;
		} else {
			throw new ResourceNotFoundException("Page not Found");
		}
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity <User> getUserByID(@PathVariable(value="id") long uid) throws ResourceNotFoundException {
		User user = user_repo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/user/create")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserData user) {
		User newUser = new User();
		try {
			if(NumberUtils.isCreatable(user.getFirst_name()) || NumberUtils.isCreatable(user.getLast_name()) ){
				return ResponseEntity.badRequest().body("Error: Names can't contain digits");
			}
			newUser.setLast_name(user.getLast_name());
			newUser.setFirst_name(user.getFirst_name());
			user_repo.save(newUser);
			return ResponseEntity.ok().body("User Created");
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
		}
	}

	
	@PutMapping("/user/update/{id}")
	public ResponseEntity<String> updateUser( @Valid @RequestBody UserData userDetails,@PathVariable(value = "id") Long uid) throws reqValidationException {
	        User user = user_repo.findById(uid).orElseThrow(() -> new reqValidationException("User not found"));
			//System.out.println(userDetails.getFirst_name()+" "+userDetails.getLast_name());
			try {
				if(NumberUtils.isCreatable(userDetails.getFirst_name()) || NumberUtils.isCreatable(userDetails.getLast_name()) ){
					return ResponseEntity.badRequest().body("Error: Names can't contain digits");
				}
				user.setLast_name(userDetails.getLast_name());
				user.setFirst_name(userDetails.getFirst_name());
				user_repo.save(user);
				return ResponseEntity.ok().body("User with ID: "+uid.toString()+" Updated");
			} catch(Exception e) {
				return ResponseEntity.badRequest().body(e.toString());
			}
	 }

	 @DeleteMapping("/user/delete/{id}")
	 public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long uid) throws ResourceNotFoundException {
	        User user = user_repo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("User not found"));
			try{
				user_repo.delete(user);
				return ResponseEntity.ok().body("User with ID: "+uid.toString()+" Deleted" );
			} catch(Exception e) {
				return ResponseEntity.badRequest().body(e.toString());
			}
	 }

}