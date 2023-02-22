package bg.fibank.ccrdays.repository;

import java.sql.PreparedStatement;

import bg.fibank.ccrdays.model.CcrDays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CcrDaysRepositoryJdbc implements CcrDaysRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int checkCcrDays(CcrDays ccrDays) throws DataAccessException {
        String sql = "INSERT INTO dbtb_ccr_days (id, personal_id, req_id) VALUES (seq_notif.nextval, ?, ?)";
    /*
        int rowsAffected = 0;

        rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ccrDays.getEgnNumber());
            preparedStatement.setString(2, ccrDays.getLoanRequest());
            return preparedStatement;
        });

        return rowsAffected;
*/

        return jdbcTemplate.update(sql, preparedStatement -> {
            preparedStatement.setString(1, ccrDays.getEgnNumber());
            preparedStatement.setString(2, ccrDays.getLoanRequest());
        });
    }


    /** ASD **/


/*
    @Override
    public int checkCcrDays(CcrDays ccrDays) {
        String sql = "insert into dbtb_ccr_days (id,personal_id,req_id) values(seq_notif.nextval,?,?)";

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(conn -> {

            // Pre-compiling SQL
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println(ccrDays.getEgnNumber() + ccrDays.getLoanRequest() );
            // Set parameters
            preparedStatement.setString(1, ccrDays.getEgnNumber());
            preparedStatement.setString(2, ccrDays.getLoanRequest());

            return preparedStatement;

        }, generatedKeyHolder);

        System.out.println(generatedKeyHolder.getKeys());

        return rowsAffected;
    }
*/


    @Override
    public String checkLoanRequest(CcrDays ccrDays) {
        String sql = "select id from requests where id = ?";
        System.out.println("checkLoanRequest: " + ccrDays.getLoanRequest() );

        return jdbcTemplate.queryForObject(sql,
                new Object[] { ccrDays.getLoanRequest() },
                String.class
        );
    }

    @Override
    public int checkLoanRequestInt(CcrDays ccrDays) throws DataAccessException {
        String sql = "select count(1) from requests where id = ? and egn = ?";
        System.out.println("checkLoanRequestInt: " + ccrDays.getLoanRequest() + " - " + ccrDays.getEgnNumber());
        Object[] args = new Object[] { ccrDays.getLoanRequest(), ccrDays.getEgnNumber() };
        return jdbcTemplate.queryForObject(sql, args, Integer.class);
    }

    @Override
    public int updateSessionTime(String authToken) {

        String sql = "update users set SESSION_EXPIRY_DT = sysdate + 1/24 where token = ?";
        System.out.println(authToken);

        return jdbcTemplate.update(sql,
                authToken
        );
    }

}