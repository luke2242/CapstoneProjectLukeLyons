package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.mappers.UserMapper;
import com.example.pulselist.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDTO saveUser(UserDTO user){
        User entity = userMapper.toEntity(user);
        return userMapper.toDto(userRepo.save(entity));
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



}
