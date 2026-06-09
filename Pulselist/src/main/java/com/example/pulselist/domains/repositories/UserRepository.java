package com.example.pulselist.domains.repositories;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(UserDTO userDto);

}
