package ita.tw.afs.spark.service;

import ita.tw.afs.spark.model.Logs;
import ita.tw.afs.spark.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;

    public Iterable<Logs> getLogs(Long userId, String userType) {
        return logsRepository.findByUserIdAndUserType(userId, userType);
    }

    public Logs createLogs(Long userId, String userType, String action, String actionDetails, String applicationTime) {
        Logs logs = new Logs();
        logs.setUserId(userId);
        logs.setUserType(userType);
        logs.setAction(action);
        logs.setActionDetails(actionDetails);
        logs.setDateTime(applicationTime);
        return logsRepository.save(logs);
    }
}
