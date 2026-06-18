package com.example.pulselist.service.serviceImpl;

import com.example.pulselist.domains.dto.CreateUserDTO;
import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import com.example.pulselist.exceptions.AccountAlreadyExistsException;
import com.example.pulselist.exceptions.InvalidUserIDException;
import com.example.pulselist.service.mappers.UserMapper;
import com.example.pulselist.service.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.UserRecord.CreateRequest;


import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final FirebaseAuth firebaseAuth;


    public UserServiceImpl(UserRepository userRepo, UserMapper userMapper, FirebaseAuth firebaseAuth) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.firebaseAuth = firebaseAuth;
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
        // Calls the firebaseUserMethod and creates the email and password
        String firebaseUid = createFirebaseUser(createUserDTO.getEmail(), createUserDTO.getPassword());

        // Converts it to entity
        User entity = new User();
        entity.setEmail(createUserDTO.getEmail());
        entity.setUsername(createUserDTO.getUsername());
        entity.setFirebaseUid(firebaseUid);

        User save = userRepo.save(entity);
        return userMapper.toDto(save);
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

    @Override
    public String createFirebaseUser(String email, String password) {
        CreateRequest req = new CreateRequest();

        // Sets the parameters
        req.setEmail(email);
        req.setPassword(password);
        req.setEmailVerified(true);

        // Attempts to create new firebase account
        try{
            UserRecord userRecord = firebaseAuth.createUser(req);
            // Returns the firebase uid that we can use for our user
            return userRecord.getUid();
        } catch (FirebaseAuthException exception){

            // If the account already exists we throw an exception indicating so
            if(exception.getMessage().contains("DUPLICATE_ACCOUNT_ERRORR")){

                throw new AccountAlreadyExistsException("This user is already registered.");
            }

            throw new RuntimeException("Firebase error: " + exception.getMessage());
        }
    }


}
