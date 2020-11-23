package com.aiocdwacs.awacscloudauthserver.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aiocdwacs.awacscloudauthserver.model.User;
import com.aiocdwacs.awacscloudauthserver.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@Validated
class UserController {

   private final UserRepository repository;

   private final PasswordEncoder passwordEncoder;

   UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
       this.repository = repository;
       this.passwordEncoder = passwordEncoder;
   }
   
   @GetMapping("/me")
   @PreAuthorize("hasRole('SYSTEM')")
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

//   @GetMapping("/findByEmail")
//   @PreAuthorize("!hasAuthority('SYSTEM') || (authentication.principal == #email)")
//   User findByEmail(@RequestParam String email, OAuth2Authentication authentication) {
//       return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(User.class, "email", email));
//   }
//
//   @GetMapping("/{id}")
//   @PostAuthorize("!hasAuthority('SYSTEM') || (returnObject != null && returnObject.email == authentication.principal)")
//   User one(@PathVariable Long id) {
//       return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
//   }

//   @PutMapping("/{id}")
//   @PreAuthorize("!hasAuthority('SYSTEM') || (authentication.principal == @userRepository.findById(#id).orElse(new net.reliqs.gleeometer.users.User()).email)")
//   void update(@PathVariable Long id, @Valid @RequestBody User res) {
//       User u = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
//       res.setPassword(u.getPassword());
//       res.setGlee(u.getGlee());
//       repository.save(res);
//   }

   @PostMapping
   @PreAuthorize("!hasAuthority('SYSTEM')")
   User create(@RequestBody User res) {
       return repository.save(res);
   }

   @DeleteMapping("/{id}")
   @PreAuthorize("!hasAuthority('SYSTEM')")
   void delete(@PathVariable Long id) {
       if (repository.existsById(id)) {
           repository.deleteById(id);
       } else {
           throw new UsernameNotFoundException(String.valueOf(id));
       }
   }

//   @PutMapping("/{id}/changePassword")
//   @PreAuthorize("hasAuthority('SYSTEM') || (#oldPassword != null && !#oldPassword.isEmpty() && authentication.principal == @userRepository.findById(#id).orElse(new net.reliqs.gleeometer.users.User()).email)")
//   void changePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @RequestParam String newPassword) {
//       User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
//       if (oldPassword == null || oldPassword.isEmpty() || passwordEncoder.matches(oldPassword, user.getPassword())) {
//           user.setPassword(passwordEncoder.encode(newPassword));
//           repository.save(user);
//       } else {
//           throw new ConstraintViolationException("old password doesn't match", new HashSet()<String>);
//       }
}