package com.s305089.software.login.dao;

import com.s305089.software.login.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private static final Logger log = LogManager.getRootLogger();

    private ClientDao dao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }




    public Client findByEmail(String email) {
        return dao.findByEmail(email);
    }


    public <S extends Client> S save(S entity) {
        String password = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(password);
        return dao.save(entity);
    }

    public <S extends Client> S saveWithoutPassword(S entity) {
        return dao.save(entity);
    }

    public Optional<Client> findById(Integer integer) {
        return dao.findById(integer);
    }


    public boolean existsById(Integer integer) {
        return dao.existsById(integer);
    }


    public Iterable<Client> findAll() {
        return dao.findAll();
    }

    public long count() {
        return dao.count();
    }


    public void deleteById(Integer integer) {
        dao.deleteById(integer);
    }


    public void delete(Client entity) {
        dao.delete(entity);
    }


    public void deleteAll(Iterable<? extends Client> entities) {
        dao.deleteAll(entities);
    }


    public void deleteAll() {
        dao.deleteAll();
    }
}
