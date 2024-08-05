package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.User;
import bg.moviebox.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  private UserServiceImpl toTest;

  @Captor
  private ArgumentCaptor<User> userEntityCaptor;

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private PasswordEncoder mockPasswordEncoder;

  @BeforeEach
  void setUp() {

    toTest = new UserServiceImpl(
            mockUserRepository,
            mockPasswordEncoder,
            new ModelMapper()
        );

  }

  @Test
  void testUserRegistration() {
    // Arrange

    UserRegistrationDTO userRegistrationDTO =
        new UserRegistrationDTO()
            .setFirstName("Pesho")
            .setLastName("Ivanov")
            .setPassword("parola")
            .setEmail("pesho@example.com");

    when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
        .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());


    // ACT
    toTest.registerUser(userRegistrationDTO);
    // in register comes DTO and you can't check what happen with that DTO, is it mapped correct,
    //did it go to entity

    // Assert
    verify(mockUserRepository).save(userEntityCaptor.capture()); // with capture you can take the DTO and see it

    User actualSavedEntity = userEntityCaptor.getValue();

    Assertions.assertNotNull(actualSavedEntity);
    Assertions.assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
    Assertions.assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
    Assertions.assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
        actualSavedEntity.getPassword());
    Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());
  }

}
