package bg.moviebox.web;

import bg.moviebox.model.entities.User;
import bg.moviebox.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegistration() throws Exception {

        // it's a form not an APPLICATION.JSON
        mockMvc.perform(post("/users/register")
                        .param("email", "pesho@example.com")
                        .param("firstName", "Pesho")
                        .param("lastName", "Ivanov")
                        .param("password", "parola")
                        .param("confirmPassword", "parola")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Optional<User> userEntityOpt = userRepository.findByEmail("pesho@example.com");

        Assertions.assertTrue(userEntityOpt.isPresent());

        User user = userEntityOpt.get();

        Assertions.assertEquals("Pesho", user.getFirstName());
        Assertions.assertEquals("Ivanov", user.getLastName());

        Assertions.assertTrue(passwordEncoder.matches("parola", user.getPassword()));
    }
}

