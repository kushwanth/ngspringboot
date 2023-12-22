package com.crud.simple.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.simple.model.Admin;
import com.crud.simple.repository.AdminRepository;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("superuser")
public class AdminController{
	
	@Autowired
	private AdminRepository admin_repo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
	public ResponseEntity createAdmin(@Valid @RequestBody Admin admin){
        /* Admin newAdmin = new Admin();
        try{
        newAdmin.setEmail(admin.getEmail());
        newAdmin.setPhone(admin.getPhone());
        newAdmin.setFirst_name(admin.getFirst_name());
        newAdmin.setLast_name(admin.getLast_name());
        newAdmin.setUsername(admin.getUsername());
        String hash = encoder().encode(admin.getPassword());
        newAdmin.setPassword(hash);
		}
        catch(Exception e) {
            System.out.println(e);
        } */
        admin.setPassword(encoder.encode(admin.getPassword()));
        try{
            admin_repo.save(admin);
            return ResponseEntity.ok().body("User Registered");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("User Registration Failed. "+e.toString());
        }
    }
}
