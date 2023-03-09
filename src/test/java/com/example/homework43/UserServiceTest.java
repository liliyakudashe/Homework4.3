package com.example.homework43;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    void whenUsersInRepository(){
        when(userRepository.getAllUsers())
                .thenReturn(List.of(new User("Login1", 425), new User("Login2", 582)));
      assertThat(userService.getAllUser()).isEqualTo(1007);

    }

    @Test
    void whenRepositoryIsEmptyThenCountShouldBeZero(){
        when(userRepository.getAllUsers())
                .thenReturn(List.of());
        assertThat(userService.getAllUser()).isEqualTo(0);
    }

    @Test
    void whenRepositoryReturnsNullThenSomethingHappened(){
        when(userRepository.getAllUsers())
                .thenReturn(null);
        assertThat(userService.getAllUser()).isEqualTo(0);
    }

    @Test
    void whenCorrectUserIsAddedThenIsCalledFromRepo(){
        when(userRepository.getAllUsers()).thenReturn(List.of());
        when(userRepository.addUsers(any(User.class)));
        userService.addUser("Test1", 478);
        verify(userRepository.addUsers(any()));
    }

    @Test
    void whenInvalidUsersPassedThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.addUser("", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Логин не может быть пустым");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUsers(any());
    }

    @Test
    void whenExistingUsersPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("Test2", 256)));
        assertThatThrownBy(() -> userService.addUser("Test2", 256))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Логин и пароль уже существуют");
    }

    @Test
    void  whenNetworkExceptionIsRaisedThenServiceReturnZero(){
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException());
        assertThat(userService.getAllUser()).isEqualTo(0);
    }
}
