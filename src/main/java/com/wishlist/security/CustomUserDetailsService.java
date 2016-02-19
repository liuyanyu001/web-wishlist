package com.wishlist.security;


import com.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service(value = "CustomUserDetailsService")
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
    	
    	com.wishlist.model.User user;
		user = userRepository.findByLogin(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		

		return new UserContext(user);
    }
    
}
