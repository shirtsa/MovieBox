package bg.moviebox.service;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.user.MovieBoxUserDetails;

import java.util.Optional;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    Optional<MovieBoxUserDetails> getCurrentUser();
}
