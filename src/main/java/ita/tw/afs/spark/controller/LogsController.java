package ita.tw.afs.spark.controller;

import ita.tw.afs.spark.model.Logs;
import ita.tw.afs.spark.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value ="/spark/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    @GetMapping(value = "/{userId}/{userType}",produces = APPLICATION_JSON_VALUE)
    public Iterable<Logs> getListOfLogsByUserId(@PathVariable Long userId, @PathVariable String userType) {
        return logsService.getLogs(userId,userType);
    }
}
