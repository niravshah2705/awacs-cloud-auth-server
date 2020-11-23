package com.aiocdwacs.awacscloudauthserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiocdwacs.awacscloudauthserver.model.AuthUserDetail;
import com.aiocdwacs.awacscloudauthserver.model.User;
import com.aiocdwacs.awacscloudauthserver.repository.UserDetailsRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userDetailsRepository.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(name);
        }
        
        AuthUserDetail userDetails = new AuthUserDetail(user);
        AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();
        checker.check(userDetails);
        
        return userDetails;
    }

	public UserDetailServiceImpl() {
		super();
	}
    
    
}