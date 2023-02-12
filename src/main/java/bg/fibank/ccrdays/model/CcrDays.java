package bg.fibank.ccrdays.model;

public class CcrDays {

    private String egnNumber;
    private String loanRequest;

    public CcrDays(String egnNumber, String loanRequest)  {
        this.egnNumber = egnNumber;
        this.loanRequest = loanRequest;
    }

    public String getEgnNumber() {
        return egnNumber;
    }

    public String getLoanRequest() {
        return loanRequest;
    }

}
