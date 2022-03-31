package dk.kvalitetsit.hello.dao;

import dk.kvalitetsit.hello.dao.entity.Result;
import dk.kvalitetsit.hello.dao.entity.VulnerabilityCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class TrivyDaoImpl implements TrivyDao {
    private static final Logger logger = LoggerFactory.getLogger(TrivyDaoImpl.class);
    private final DataSource dataSource;

    public TrivyDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long insertResult(Result result) {
        logger.info("Inserting new entry in database.");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        var sql = "insert into result(name, scan_time, created) values(:name, :scanTime, now())";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

//        var parameterMap = Map.of("name", result.getName(),
//                                 "scanTime", result.getScanTime());

        template.update(sql, new BeanPropertySqlParameterSource(result), keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Long insertVulnerabilityCount(VulnerabilityCount vulnerabilityCount) {
        logger.info("Inserting new entry in database.");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        var sql = "insert into vulnerability_count(result_id, severity, count) values(:resultId, :severity, :count)";
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);

        template.update(sql, new BeanPropertySqlParameterSource(vulnerabilityCount), keyHolder);

        return keyHolder.getKey().longValue();
    }
}
