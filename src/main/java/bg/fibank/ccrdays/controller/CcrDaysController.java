package bg.fibank.ccrdays.controller;

import java.util.HashMap;
import java.util.Map;
import bg.fibank.ccrdays.model.CcrDays;
import bg.fibank.ccrdays.service.CcrDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CcrDaysController {
    @Autowired
    CcrDaysService ccrDaysService;

    @PostMapping("/check")
    public ResponseEntity<Map<String, String>> checkCcrRecord(
            @RequestHeader("auth-token") String authToken,
            @RequestBody CcrDays ccrDays) {

        Map<String, String> json = new HashMap<>();
        try {
            String result = ccrDaysService.checkCcrDays(ccrDays, authToken);
            json.put("result", result);
        } catch (IllegalArgumentException ex) {
            json.put("error", ex.getMessage());
        } catch (Exception e) {
            //json.put("error", "not found"); // PROD
            json.put("error", String.valueOf(e)); // DEV
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}