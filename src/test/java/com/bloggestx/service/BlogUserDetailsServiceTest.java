package com.bloggestx.service;

import com.bloggestx.model.User;
import com.bloggestx.model.enums.Role;
import com.bloggestx.model.enums.RoleDescription;
import com.bloggestx.model.principal.BlogUserPrincipal;
import com.bloggestx.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName(" Testing BlogUserDetails Service")
@ExtendWith(MockitoExtension.class)
public class BlogUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BlogUserDetailsService userService;

    private User user;

    @BeforeEach
    public void setUp() {
         user = User.builder()
                .id("1")
                .username("User")
                .password("Password")
                .role(Role.USER)
                .description(RoleDescription.USER)
                .build();
    }

    @Test
    @DisplayName("findAll should return a list of all users")
    public void findAll_ReturnsListOfUsers() {

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        assertEquals(users.size(), 1);
        assertEquals(users.get(0), user);
    }

    @Test
    @DisplayName("findByUserName should return User if User exists")
    public void findByUserName_ReturnsUser_ifUserExists() {

        when(userRepository.findByUsername("User")).thenReturn(user);

        User retrievedUser = userService.findByUserName("User");

        assertEquals(retrievedUser, user);
        assertEquals(retrievedUser.getUsername(), user.getUsername());

    }

    @Test
    @DisplayName("findByUserName should return User if User exists")
    public void findByUserName_returnsNull_ifUserDoesNotExist() {

        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        User retrievedUser = userService.findByUserName("DoesNotExist");

        Assertions.assertNull(retrievedUser);
    }

    @Test
    @DisplayName("loadByUserName should return UserPrincipal if user exists")
    public void loadByUserName_ReturnsUser_ifUserExists() {
        UserDetails expectedUserPrincipal = new BlogUserPrincipal(user);

        when(userRepository.findByUsername("User")).thenReturn(user);

        UserDetails retrievedUserPrincipal = userService.loadUserByUsername("User");

        Assertions.assertNotNull(retrievedUserPrincipal);
        Assertions.assertAll("Grouped assertions for UserPrincipal",
                () -> assertEquals(expectedUserPrincipal.getUsername(), retrievedUserPrincipal.getUsername()),
                () -> assertEquals(expectedUserPrincipal.getAuthorities(), retrievedUserPrincipal.getAuthorities()),
                () -> assertEquals(
                        expectedUserPrincipal.isAccountNonExpired(), retrievedUserPrincipal.isAccountNonExpired()
                ),
                () -> assertEquals(
                        expectedUserPrincipal.isAccountNonLocked(), retrievedUserPrincipal.isAccountNonLocked()
                )
        );
    }

    @Test
    @DisplayName("loadByUserName should throw UsernameNotFoundException if user does not exist")
    public void loadByUsername_throwsException_ifUserDoesNotExist() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("DoesNotExist"));
    }

    @Test
    @DisplayName("save should call userRepository 1 time")
    public void when_save_isSuccessful() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("deleteAll should call userRepository 1 time")
    public void when_deleteAll_isSuccessful() {
        doNothing().when(userRepository).deleteAll();

        userService.deleteAll();

        verify(userRepository, times(1)).deleteAll();
    }

}
