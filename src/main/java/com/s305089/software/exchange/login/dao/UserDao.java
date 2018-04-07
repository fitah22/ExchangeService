package com.s305089.software.exchange.login.dao;

import com.s305089.software.exchange.login.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserDao extends CrudRepository<Client, Integer> {
    Client findByEmail(String email);

}
