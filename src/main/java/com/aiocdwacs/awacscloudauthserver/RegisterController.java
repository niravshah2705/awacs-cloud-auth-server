package com.aiocdwacs.awacscloudauthserver;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiocdwacs.awacscloudauthserver.model.Permission;
import com.aiocdwacs.awacscloudauthserver.model.Role;
import com.aiocdwacs.awacscloudauthserver.model.User;
import com.aiocdwacs.awacscloudauthserver.repository.PermissionsRepository;
import com.aiocdwacs.awacscloudauthserver.repository.UserDetailsRepository;

@RestController
@RequestMapping("/api/user/")
public class RegisterController {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private PermissionsRepository permissionsRepository;

	@PostMapping("/register")
	public String doRegister(@RequestParam String username, @RequestParam String password ) {
		String encodedPassword  = passwordEncoder.encode(password);

		User user = new User();
		user.setEnabled(Boolean.TRUE);
		user.setPassword(encodedPassword);
		user.setUsername(username);

		Role userRole = new Role();
		List<Permission> defaultPermissions = Arrays.asList(
				new Permission("ROLE_SCRUM"), 
				new Permission("ROLE_BOARD"),
				new Permission("ROLE_API_ACCESS"), 
				new Permission("ROLE_TRUSTED_CLIENT"));
		
		List<Permission> availablePermissions = permissionsRepository.findAll();
		
		if(availablePermissions.size() == 0) {
			userRole.setPermissions(defaultPermissions);
		}else {
			userRole.setPermissions(availablePermissions);
		}
		user.setRoles(Arrays.asList(userRole));
		
		userRepository.save(user);

		return "register-success";
	}
}
