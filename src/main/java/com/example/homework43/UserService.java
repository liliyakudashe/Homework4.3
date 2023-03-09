package com.example.homework43;

import java.util.Collection;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String loginUser, int passwordUser){
        User user = new User(loginUser, passwordUser);

        if (loginUser == null || loginUser.isBlank()){
            throw new IllegalArgumentException("Логин не может быть пустым");
        }

       boolean userExist = this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(u -> u.equals(user));
       if (userExist){
           throw new IllegalArgumentException("Логин и пароль уже существуют");
       }

       this.userRepository.addUsers(user);
    }

    public int getAllUser(){
        try {
            Collection<User> users = this.userRepository
                    .getAllUsers();
            if (users == null){
                return 0;
        }
            return users
                    .stream()
                    .mapToInt(User::getPassword)
                    .sum();
        } catch (RuntimeException e){
            return 0;
        }

    }

}
