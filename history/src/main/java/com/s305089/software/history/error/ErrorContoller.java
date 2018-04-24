package com.s305089.software.history.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com"})
@RestController()
public class ErrorContoller {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    ErrorMessageDao errorMessageDao;

    @PostMapping(value = "/error")
    public void logOrder(@RequestBody ErrorMessage errorMessage) {
        errorMessageDao.save(errorMessage);
        log.info("Error message saved");
    }

    @GetMapping(value = "/error")
    public Iterable<ErrorMessage> getAllErrors() {
        return errorMessageDao.findAll();
    }

}
