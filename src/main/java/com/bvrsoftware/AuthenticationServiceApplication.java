package com.bvrsoftware;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bvrsoftware.model.Role;
import com.bvrsoftware.repository.RoleRepository;

@SpringBootApplication
public class AuthenticationServiceApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		try {
			Role role1=new Role();
			role1.setRole_id(55L);
			role1.setRole_name("ADMIN");
			Role role2=new Role();
			role2.setRole_id(60L);
			role2.setRole_name("ROLE_USER");
			
			List<Role> roles=List.of(role1,role2);
			
			List<Role> result=this.roleRepository.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getRole_name());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
