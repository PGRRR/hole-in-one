package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Members;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    /**
     * Spring DataSource 주입 (injection 인젝션)
     */

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Members save(Members members) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", members.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        members.setId(key.longValue());
        return members;
    }

    @Override
    public Members update(Members members) {
        return null;
    }

    @Override
    public Optional<Members> findById(Long id) {
        List<Members> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper());
        return result.stream().findAny();
    }

    @Override
    public Optional<Members> findByEmail(String email) {
        List<Members> result = jdbcTemplate.query("select * from member where email = ?", memberRowMapper());
        return result.stream().findAny();
    }

    @Override
    public List<Members> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Members> memberRowMapper() {
        return (rs, rowNum) -> {
            Members members = new Members();
            members.setId(rs.getLong("id"));
            members.setName(rs.getString("name"));
            return members;
        };
    }
}
