package com.example.homework43;

import java.util.*;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public User addUsers(User user){
        this.users.add(user);
        return user;
    }

    public Collection<User> getAllUsers(){
        return Collections.unmodifiableCollection(users);
    }


}
