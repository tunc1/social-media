package app.service;

import app.exception.EmailAlreadyInUseException;
import app.exception.UsernameAlreadyInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.User;
import app.repository.UserRepository;

@Service
public class UserService
{
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder)
	{
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	public void register(User user)
	{
		if(userRepository.existsByUsername(user.getUsername()))
			throw new UsernameAlreadyInUseException();
		if(userRepository.existsByEmail(user.getEmail()))
			throw new EmailAlreadyInUseException();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}