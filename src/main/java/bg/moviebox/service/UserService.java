package bg.moviebox.service;

import bg.moviebox.model.dtos.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
