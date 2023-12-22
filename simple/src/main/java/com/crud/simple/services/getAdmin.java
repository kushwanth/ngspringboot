package com.crud.simple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.simple.model.Admin;
import com.crud.simple.repository.AdminRepository;

@Service
public class getAdmin implements UserDetailsService {
    
    @Autowired
    private AdminRepository admin_repo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Admin admin = admin_repo.findByUsername(username);
        if (admin == null) throw new UsernameNotFoundException(username);
        System.out.println(admin.getUsername()+"-"+admin.getPassword());
        return User.withUsername(admin.getUsername()).password(admin.getPassword()).authorities("write").build();
    }

}
