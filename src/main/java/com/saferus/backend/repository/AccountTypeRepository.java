/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend.repository;

import com.saferus.backend.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucasbrito
 */

//Repositório de Tipo de Contas
@Repository("atRepository")
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    
    AccountType findAccountTypeById(int id);
    
}
