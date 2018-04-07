package com.s305089.software.login.dao;

import com.s305089.software.login.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClientDao extends CrudRepository<Client, Integer> {
    Client findByEmail(String email);

}
