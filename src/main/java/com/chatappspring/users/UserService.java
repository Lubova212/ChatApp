package com.chatappspring.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void createUser(User user)throws Exception{
        this.userRepository.save(user);

    }

    public User verifyUser(String username, String password) throws Exception {
        User user = this.userRepository.findByUsernameAndPassword(username,password);
        if (user == null) throw new Exception("Username or password is not correct");
        return user;
    }

    public User findUserById(long userId) throws Exception{
        return this.userRepository.findById(userId).orElseThrow(()-> new Exception("User with id " +userId + " not found"));
    }

    public User findUserByEmail(String email) throws Exception{
        User user = this.userRepository.findByEmail(email);
        if (user == null) throw new Exception("User with email " + email + " not found");
        return user;
    }
}

