package bg.moviebox.service.impl;

import bg.moviebox.model.dtos.UserRegistrationDTO;
import bg.moviebox.model.entities.Production;
import bg.moviebox.model.entities.User;
import bg.moviebox.model.entities.UserRoleEntity;
import bg.moviebox.model.enums.UserRoleEnum;
import bg.moviebox.model.user.MovieBoxUserDetails;
import bg.moviebox.repository.ProductionRepository;
import bg.moviebox.repository.UserRepository;
import bg.moviebox.repository.UserRoleRepository;
import bg.moviebox.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl toTest;

  @Captor
  private ArgumentCaptor<User> userEntityCaptor;

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private ProductionRepository mockProductionRepository;

  @Mock
  private UserRoleRepository mockUserRoleRepository;

  @Mock
  private PasswordEncoder mockPasswordEncoder;


  private static final String TEST_EMAIL = "user@example.com";

  @BeforeEach
  void setUp() {

    toTest = new UserServiceImpl(
            mockUserRepository,
            mockProductionRepository,
            mockUserRoleRepository,
            mockPasswordEncoder,
            new ModelMapper()
        );

  }

  @Test
  void testUserRegistrationFirstUser() {
    // Arrange
    UserRegistrationDTO userRegistrationDTO =
        new UserRegistrationDTO()
            .setFirstName("Pesho")
            .setLastName("Ivanov")
            .setPassword("parola")
            .setEmail("pesho@example.com");

    UserRoleEntity adminRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.ADMIN);
    UserRoleEntity userRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.USER);

    when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
        .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());
            when(mockUserRepository.count()).thenReturn(0L);

    List<UserRoleEntity> allRoles = List.of(adminRoleEntity, userRoleEntity);
    when(mockUserRoleRepository.findAll()).thenReturn(allRoles);

    // ACT
    toTest.registerUser(userRegistrationDTO);
    // in register comes DTO and you can't check what happen with that DTO, is it mapped correct,
    //did it go to entity

    // Assert
    verify(mockUserRepository).save(userEntityCaptor.capture()); // with capture you can take the DTO and see it

    User actualSavedEntity = userEntityCaptor.getValue();

    // Debug output
    System.out.println("Actual saved user: " + actualSavedEntity);
    if (actualSavedEntity == null) {
      fail("User entity should not be null");
    } else {
      System.out.println("Roles in actual saved entity: " + actualSavedEntity.getRoles());
    }

    assertNotNull(actualSavedEntity);
    assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
    assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
    assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
        actualSavedEntity.getPassword());
    assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());

    // Check roles assignment for the first user
    assertEquals(2, actualSavedEntity.getRoles().size());
    assertTrue(actualSavedEntity.getRoles().stream()
            .anyMatch(role -> role.getRoles() == UserRoleEnum.ADMIN));
    assertTrue(actualSavedEntity.getRoles().stream()
            .anyMatch(role -> role.getRoles() == UserRoleEnum.USER));
  }

  @Test
  void testUserRegistrationSubsequentUser() {
    // Arrange
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO()
            .setFirstName("Ivan")
            .setLastName("Petrov")
            .setPassword("password")
            .setEmail("ivan@example.com");

    UserRoleEntity adminRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.ADMIN);
    UserRoleEntity userRoleEntity = new UserRoleEntity().setRoles(UserRoleEnum.USER);

    when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
            .thenReturn(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword());
    when(mockUserRepository.count()).thenReturn(1L);

    List<UserRoleEntity> allRoles = List.of(adminRoleEntity, userRoleEntity);
    when(mockUserRoleRepository.findAll()).thenReturn(allRoles);

    // Act
    toTest.registerUser(userRegistrationDTO);

    // Assert
    verify(mockUserRepository).save(userEntityCaptor.capture());

    User actualSavedEntity = userEntityCaptor.getValue();

    assertNotNull(actualSavedEntity);
    assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
    assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
    assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
            actualSavedEntity.getPassword());
    assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());

    // Check roles assignment for subsequent users
    assertEquals(1, actualSavedEntity.getRoles().size()); // USER only
    assertTrue(actualSavedEntity.getRoles().stream()
            .anyMatch(role -> role.getRoles() == UserRoleEnum.USER));
  }

  @Test
  void addToPlaylist_ShouldAddProductionToUserPlaylist() {
    // Arrange
    Long userId = 1L;
    UUID userUuid = UUID.randomUUID();
    Long productionId = 1L;
    User user = new User()
            .setEmail(TEST_EMAIL)
            .setPassword("parola")
            .setFirstName("Gosho")
            .setLastName("Ivanov")
            .setRoles(List.of(
                    new UserRoleEntity().setRoles(UserRoleEnum.ADMIN),
                    new UserRoleEntity().setRoles(UserRoleEnum.USER)
            ));
    Production production = new Production();

    when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(mockProductionRepository.findById(productionId)).thenReturn(Optional.of(production));


    MovieBoxUserDetails userDetails = new MovieBoxUserDetails(
            userId,
            userUuid,
            TEST_EMAIL,
            "parola",
            Collections.emptyList(),  // Authorities, could be empty for testing
            "Gosho",
            "Ivanov"
    );

    // Act
    toTest.addToPlaylist(productionId, userDetails);

    // Assert
    verify(mockUserRepository).save(user);
    assertTrue(user.getPlaylist().contains(production));
  }


  @Test
  void removeFromPlaylist_ShouldRemoveProductionFromUserPlaylist() {
    // Arrange
    Long userId = 1L;
    UUID userUuid = UUID.randomUUID();
    Long productionId = 1L;
    User user = new User()
            .setEmail(TEST_EMAIL)
            .setPassword("parola")
            .setFirstName("Gosho")
            .setLastName("Ivanov")
            .setRoles(List.of(
                    new UserRoleEntity().setRoles(UserRoleEnum.ADMIN),
                    new UserRoleEntity().setRoles(UserRoleEnum.USER)
            ));
    Production production = new Production();
    user.getPlaylist().add(production);

    MovieBoxUserDetails userDetails = new MovieBoxUserDetails(
            userId,
            userUuid,
            TEST_EMAIL,
            "parola",
            Collections.emptyList(),  // Authorities
            "Gosho",
            "Ivanov"
    );

    when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
    when(mockProductionRepository.findById(productionId)).thenReturn(Optional.of(production));

    // Act
    toTest.removeFromPlaylist(productionId, userDetails);

    // Assert
    verify(mockUserRepository).save(user);
    assertFalse(user.getPlaylist().contains(production));
  }

  @Test
  void getUserPlaylist_ShouldReturnUserPlaylist() {
    // Arrange
    Long userId = 1L;
    User user = new User()
            .setEmail("pesho@example.com")
            .setPassword("parola")
            .setFirstName("Pesho")
            .setLastName("Goshov")
            .setRoles(List.of(
                    new UserRoleEntity().setRoles(UserRoleEnum.USER)
            ));

    Production production = new Production();
    Set<Production> playlist = Set.of(production);
    user.setPlaylist(playlist);

    when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));

    MovieBoxUserDetails userDetails = new MovieBoxUserDetails(
            userId,
            null,
            "pesho@example.com",
            "parola",
            Collections.emptyList(),
            "Pesho",
            "Goshov"
    );

    // Act
    Set<Production> result = toTest.getUserPlaylist(userDetails);

    // Assert
    assertEquals(playlist, result);
  }


}
