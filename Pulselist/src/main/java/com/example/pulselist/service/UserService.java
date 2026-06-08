package com.example.pulselist.service;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Returns list of users
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    // Returns user by id
    public User getUserById(Long id) throws InvalidUserIDException {
        return userRepo.findById(id)
                .orElseThrow(() -> new InvalidUserIDException("Invalid user ID, no user could be found"));

    }

    // Saves a user
    public User saveUser(User user){
        return userRepo.save(user);
    }

    // Updates user
    public User updateUser(User user, Long userId){

        // Checks if the user exists
        User userDB = userRepo.findById(userId).get();

        // Checks if the firebaseuid is populated and replaces it with the new firebase uid
        if(Objects.nonNull(user.getFirebaseUid()) && !"".equalsIgnoreCase(user.getFirebaseUid())){
            userDB.setFirebaseUid(user.getFirebaseUid());
        };

        if(Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())){
            userDB.setUsername(user.getUsername());
        }

        return userRepo.save(userDB);

    }

    // Deletes user
    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }



}
