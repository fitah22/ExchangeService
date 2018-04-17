package com.s305089.software.history.error;

import com.s305089.software.history.error.ErrorMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ErrorMessageDao extends CrudRepository<ErrorMessage, Integer> {
}
