package bg.moviebox.service;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.user.MovieBoxUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    Optional<MovieBoxUserDetails> getCurrentUser();

    void addToPlaylist(Long productionId, MovieBoxUserDetails movieBoxUserDetails);

    void removeFromPlaylist(Long productionId, MovieBoxUserDetails movieBoxUserDetails);

    Set<Production> getUserPlaylist(MovieBoxUserDetails movieBoxUserDetails);
}
