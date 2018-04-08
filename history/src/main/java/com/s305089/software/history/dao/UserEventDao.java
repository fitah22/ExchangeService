package com.s305089.software.history.dao;

import com.s305089.software.history.model.UserEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEventDao extends CrudRepository<UserEvent, Integer> {
    List<UserEvent> findAllByUsername(String username);
}
