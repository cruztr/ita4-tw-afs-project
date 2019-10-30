package ita.tw.afs.spark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ita.tw.afs.spark.model.Logs;
import ita.tw.afs.spark.service.LogsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogsController.class)
@ActiveProfiles(profiles = "test")
class LogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogsService logsService;

    private Logs dummyLogs() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        Logs logs = new Logs();
        logs.setUserId(123L);
        logs.setUserType("Parking Boy");
        logs.setAction("Create");
        logs.setActionDetails("Create Order 44");
        logs.setDateTime(myDateObj.format(myFormatObj));
        return logs;
    }

    @Test
    void should_return_status_created_when_create_reservation() throws Exception {
        when(logsService.getLogs(123L,"Parking Boy")).thenReturn(Collections.singleton(dummyLogs()));

        ResultActions result = mockMvc.perform(get("/spark/logs/123/Parking Boy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dummyLogs())));
        result.andExpect(status().isOk());
    }
}