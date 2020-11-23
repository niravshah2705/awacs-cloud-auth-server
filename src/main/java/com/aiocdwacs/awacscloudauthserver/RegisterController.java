package com.aiocdwacs.awacscloudauthserver;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiocdwacs.awacscloudauthserver.model.Permission;
import com.aiocdwacs.awacscloudauthserver.model.Role;
import com.aiocdwacs.awacscloudauthserver.model.User;
import com.aiocdwacs.awacscloudauthserver.model.UserDto;
import com.aiocdwacs.awacscloudauthserver.repository.UserDetailsRepository;

@RestController
@RequestMapping("/api/user/")
public class RegisterController {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsRepository userRepository;

	@PostMapping("/register")
	public @ResponseBody ResponseEntity<User> doRegister(@RequestBody UserDto userToCreate) {
		String encodedPassword  = passwordEncoder.encode(userToCreate.getPassword());

		User user = new User();
		user.setEnabled(Boolean.TRUE);
		user.setPassword(encodedPassword);
		
		Optional<User> dbUser =  userRepository.findByUsername(userToCreate.getUsername());
		if(dbUser.isPresent()) {
			ResponseEntity.ok().body(String.format("user already exist (username: %s with email: %s)", dbUser.get().getUsername(), dbUser.get().getEmail()));
		}
		user.setUsername(userToCreate.getUsername());
		user.setEmail(userToCreate.getEmail());

		Role userRole = new Role();
		List<Permission> defaultPermissions = Arrays.asList(new Permission("ROLE_API_ACCESS"));		

		userRole.setPermissions(defaultPermissions);
		user.setRoles(Arrays.asList(userRole));
		
		return ResponseEntity.ok(userRepository.save(user));
	}
}
