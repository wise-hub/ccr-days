package bg.fibank.ccrdays.service;

import bg.fibank.ccrdays.model.CcrDays;
import bg.fibank.ccrdays.repository.CcrDaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

@Service
public class CcrDaysService {
    @Autowired
    CcrDaysRepository ccrDaysRepository;

    private static final int[] WEIGHTS = {2, 4, 8, 5, 10, 9, 7, 3, 6};
    private static final String TOKEN = "809c8bba1ec75eac4740c6762a3a3c0415ad289677b2680fdebb17a371d0384a";

    private static boolean isValidEGN(char[] egn) {
        if (egn == null || egn.length != 10) {
            return false;
        }

        int checkSum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = egn[i] - '0';
            if (digit < 0 || digit > 9) {
                return false;
            }
            checkSum += digit * WEIGHTS[i];
        }

        return (egn[9] - '0') == checkSum % 11 % 10;
    }

//    private static boolean isValidEGN(char[] egn) {
//        int checksum = IntStream.range(0, 9)
//                .map(i -> (egn[i] - '0') * WEIGHTS[i])
//                .sum() % 11 % 10;
//        return (egn[9] - '0') == checksum;
//    }


    public String checkLoanRequest(CcrDays ccrDays) {
        try {
            return ccrDaysRepository.checkLoanRequest(ccrDays);
        } catch (Exception e) {
            System.out.println(e);
            return "0";
        }
    }


    public int updateSessionTime(String authToken) {
        try {
            return ccrDaysRepository.updateSessionTime(authToken);
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    public String checkCcrDays(CcrDays ccrDays, String authToken) throws IllegalArgumentException {

        if (authToken == null || authToken.isEmpty() || !authToken.equals(TOKEN)) {
            System.out.println("invalid auth token: " + authToken + " " + TOKEN);
            throw new IllegalArgumentException("invalid auth token");
        }

        if (!isValidEGN(ccrDays.getEgnNumber().toCharArray())) {
            System.out.println("invalid egn");
            throw new IllegalArgumentException("invalid egn");
        }

        if (checkLoanRequest(ccrDays).equals(ccrDays.getLoanRequest())) {
            System.out.println("existing loan request");
            throw new IllegalArgumentException("existing loan request");
        }

        try {
            ccrDaysRepository.checkCcrDays(new CcrDays(
                    ccrDays.getEgnNumber(),
                    ccrDays.getLoanRequest()));

            return "request saved";
        } catch (Exception e) {
            System.out.println(e);
            return "error:" +  e;
        }
    }

}