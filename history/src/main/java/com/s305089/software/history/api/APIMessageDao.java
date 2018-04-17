package com.s305089.software.history.api;

import com.s305089.software.history.api.APIMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface APIMessageDao extends CrudRepository<APIMessage, Integer> {
    List<APIMessage> findAllByUsername(String username);
}
