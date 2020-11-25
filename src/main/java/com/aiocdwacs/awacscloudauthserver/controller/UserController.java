package com.aiocdwacs.awacscloudauthserver.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiocdwacs.awacscloudauthserver.model.User;
import com.aiocdwacs.awacscloudauthserver.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@Validated
class UserController {

	private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"; //RFC-2822

	private static final String PHONE_PATTERN = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
			+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
			+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

	private final UserRepository repository;
	private TokenStore tokenStore;
	private final PasswordEncoder passwordEncoder;

	UserController(UserRepository repository, PasswordEncoder passwordEncoder, TokenStore tokenStore) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.tokenStore = tokenStore;
	}

	@GetMapping("/principal")
	public ResponseEntity<Principal> get(final Principal principal) {
		return ResponseEntity.ok(principal);
	}

	@GetMapping
	Page<User> all(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable, OAuth2Authentication authentication) {
		String auth = (String) authentication.getUserAuthentication().getPrincipal();
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		return repository.findAll(pageable);
	}

	@GetMapping("/search")
	public List<User> search(@RequestParam String email, Pageable pageable, OAuth2Authentication authentication) {
		String auth = (String) authentication.getUserAuthentication().getPrincipal();
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		return repository.findAllByEmail(email);
	}

	@GetMapping("/findByEmail")
	@PreAuthorize("!hasAuthority('SYSTEM') || (authentication.principal == #email)")
	User findByEmail(@RequestParam String email, OAuth2Authentication authentication) {
		return repository.findByEmail(email);
	}

	@GetMapping("/{id}")
	@PostAuthorize("hasAuthority('SYSTEM') || (returnObject != null && returnObject.email == authentication.principal)")
	User one(@PathVariable Long id) throws UserPrincipalNotFoundException {
		Optional<User> user = repository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserPrincipalNotFoundException("unable to find user with id="+id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('SYSTEM')")
	@ResponseBody ResponseEntity<String> update(@PathVariable Long id, @RequestBody User res) throws UsernameCannotUpdateException, InvalidEmailFormatException, InvalidPhoneFormatException {

		Optional<User> u = repository.findById(id);
		if(u.isPresent()) {
			User userToSave = u.get();

			if (Objects.nonNull(res.getUsername())) {
				throw new UsernameCannotUpdateException("payload contains username, however username can not meant to be update here. Sorry");
			}

			if(Objects.nonNull(res.getEmail())) {
				if(Pattern.matches(EMAIL_PATTERN, res.getEmail())) {
					userToSave.setEmail(res.getEmail());
				}else {
					throw new InvalidEmailFormatException("Invalid email: "+res.getEmail());
				}
			}
			if(Objects.nonNull(res.getMsisdn())) {
				if(Pattern.matches(PHONE_PATTERN, res.getMsisdn())) {
					userToSave.setMsisdn(res.getMsisdn());
				}else {
					throw new InvalidPhoneFormatException("Invalid phone: "+res.getMsisdn());
				}

			}
			if(Objects.nonNull(res.getPassword())) {
				userToSave.setPassword(passwordEncoder.encode(res.getPassword()));
			}
			if(Objects.nonNull(res.getAuthorities()) && !res.getAuthorities().isEmpty()) {
				userToSave.setAuthorities(res.getAuthorities());
			}
			//				mysql bug that can not manage it on update dispite setting following
			//
			//				[...] column
			//				 updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
			//				[...]
			//				mysql> show global variables where variable_name like '%timestamp%';
			//				+---------------------------------+-------+
			//				| Variable_name                   | Value |
			//				+---------------------------------+-------+
			//				| explicit_defaults_for_timestamp | ON    |
			//				+---------------------------------+-------+
			//
			userToSave.setUpdated(LocalDateTime.now());	
			repository.save(userToSave);
			return ResponseEntity.ok("success");
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/temporary/signup")
	@PreAuthorize("hasAuthority('SYSTEM')")
	@ResponseBody ResponseEntity<User> create(@RequestBody User res, HttpServletRequest request) {
		res.setCreated(LocalDateTime.now());		//known issue
		res.setUpdated(res.getCreated());
		res.setPassword(passwordEncoder.encode(res.getPassword()));
		User u = repository.save(res);
		revoke(request);
		return ResponseEntity.ok(u);
	}

	@PostMapping("/temporary/logout")
	@PreAuthorize("hasAuthority('SYSTEM')")
	public ResponseEntity<Object> revoke(HttpServletRequest request) {
		try {
			String authorization = request.getHeader("Authorization");
			if (authorization != null && authorization.contains("Bearer")) {
				String tokenValue = authorization.replace("Bearer", "").trim();

				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				tokenStore.removeAccessToken(accessToken);

				//OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(tokenValue);
				OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
				tokenStore.removeRefreshToken(refreshToken);
			}
		} catch (Exception e) {
			return handleException(e, "Invalid access token");
		}
		return ResponseEntity.ok().body("Access token invalidated successfully");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('SYSTEM')")
	void delete(@PathVariable Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new UsernameNotFoundException(String.valueOf(id));
		}
	}

	@PutMapping("/{id}/oldpwd/changePassword")
	@PreAuthorize("hasAuthority('SYSTEM') || (#oldPassword != null && !#oldPassword.isEmpty() && authentication.principal == @userRepository.findById(#id).orElse(new net.reliqs.gleeometer.users.User()).email)")
	void changePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @RequestParam String newPassword) throws UserPrincipalNotFoundException, ChangePasswordException {
		User user = repository.findById(id).orElseThrow(() -> new UserPrincipalNotFoundException("id="+id));
		if (oldPassword == null || oldPassword.isEmpty() || passwordEncoder.matches(oldPassword, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassword));
			repository.save(user);
		} else {
			throw new ChangePasswordException("old password doesn't match");
		}
	}



	@ExceptionHandler(InvalidPhoneFormatException.class)
	public ResponseEntity<?> handle(InvalidPhoneFormatException exc) {
		//return ResponseEntity.status(1124).body(new ApiError(HttpStatus.BAD_REQUEST, exc.getMessage(), exc));
		return handleException(exc, exc.getMessage());
	}
	@ExceptionHandler(UserPrincipalNotFoundException.class)
	public ResponseEntity<?> handle(UserPrincipalNotFoundException exc) {
		return handleException(exc, exc.getMessage());
	}

	@ExceptionHandler(ChangePasswordException.class)
	public ResponseEntity<?> handle(ChangePasswordException exc) {
		return handleException(exc, exc.getMessage());
	}

	@ExceptionHandler(InvalidEmailFormatException.class)
	public ResponseEntity<?> handle(InvalidEmailFormatException exc) {
		return handleException(exc, exc.getMessage());
	}

	private ResponseEntity<Object> handleException(Exception exc, String message) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", message);
		body.put("x-powered-by", "AwacsInternational");

		body.put("errors", exc.getStackTrace()[0]);
		return new ResponseEntity<Object>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}