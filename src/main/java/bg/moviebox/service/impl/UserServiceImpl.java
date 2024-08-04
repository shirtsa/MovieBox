package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.UserRepository;
import bg.moviebox.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));
    }

    @Override
    public Optional<MovieBoxUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof MovieBoxUserDetails movieBoxUserDetails) {
            return Optional.of(movieBoxUserDetails);
        }
        return Optional.empty();
    }

    private User map(UserRegistrationDTO userRegistrationDTO) {
        User mappedUser = modelMapper.map(userRegistrationDTO, User.class);
        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        return mappedUser;
    }
}
