package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.CreateUserDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.AccountAlreadyExistsException;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.mappers.UserMapper;
import com.example.pulselist.service.services.UserService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    // Returns list of users, converts the entities to dto's
    @Override
    public List<UserDTO> getUsers(){
        // Find all users in DB, maps them to dto's and converts them to a list
        return userRepo.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    // Returns user by id
    @Override
    public UserDTO getUserById(Long id) throws InvalidUserIDException {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new InvalidUserIDException("Invalid user ID, no user could be found."));

        return new UserDTO(user.getUsername());
    }

    // Converts dto to entity saves it to the DB, and after the entity is brought back to a dto
    // This is done to keep user entity secure
    @Override
    public UserDTO saveUser(CreateUserDTO createUserDTO){
        User entity = new User();
        entity.setUsername(createUserDTO.getUsername());
        entity.setEmail(createUserDTO.getEmail());
        entity.setFirebaseUid(createUserDTO.getUid());

        User saved = userRepo.save(entity);

        return userMapper.toDto(saved);
    }

    // Updates user
    @Override
    public UserDTO updateUser(UserDTO user, Long userId){

        // Checks if the user exists
        User userDB = userRepo.findById(userId).get();

        if(Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())){
            userDB.setUsername(user.getUsername());
        }

        userRepo.save(userDB);

        return new UserDTO(userDB.getUsername());

    }

    // Deletes user
    @Override
    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }


    // Finds or creates a user in firebase
    @Override
    public UserDTO findOrCreateUser(String uid, String email, String name) {

        User user = userRepo.findByFirebaseUid(uid)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setFirebaseUid(uid);
                    newUser.setEmail(email);
                    newUser.setUsername(name);
                    return userRepo.save(newUser);
                });

        return userMapper.toDto(user);
    }

}
