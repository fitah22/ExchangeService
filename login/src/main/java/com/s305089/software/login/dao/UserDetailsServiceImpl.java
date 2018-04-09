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

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LogManager.getRootLogger();

    private ClientDao dao;

    @Autowired
    public UserDetailsServiceImpl(ClientDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = dao.findByEmail(email);
        if (client == null) {
            log.info("Client not found");
            throw new UsernameNotFoundException("Email not found");
        }
        log.info("Client with email: {}, is trying to log in", client.getEmail());

        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .build();
    }
}
