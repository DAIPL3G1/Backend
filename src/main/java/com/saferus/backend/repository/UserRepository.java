/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String>{
    
    Optional<User> findOptionalByNifOrEmail(String nif, String email);
    Optional<User> findOptionalByEmail(String email);
    User findUserByNif(String nif);
    User findUserByEmail(String email);
    List<User> findByEmail(String email);
    Boolean existsByNif(String nif);
    Boolean existsByEmail(String email);
    
}
