package bg.fibank.ccrdays.repository;

import bg.fibank.ccrdays.model.CcrDays;

public interface CcrDaysRepository {

    int checkCcrDays(CcrDays ccrDays);

    String checkLoanRequest(CcrDays ccrDays);

    int updateSessionTime(String authToken);

}

