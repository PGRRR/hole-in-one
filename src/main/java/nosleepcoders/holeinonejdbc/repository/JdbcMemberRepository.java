package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Members;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MemberRepository 인터페이스의 JDBC 구현체
 * DB 에 직접 관여
 */
@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource; // DB 와 연결하기 위해서는 DataSource 필요

    @Autowired
    public JdbcMemberRepository(DataSource dataSource) { // DataSource 는 Spring 에서 주입 받아야 한다.
        this.dataSource = dataSource;
    }

    /**
     * 저장
     */
    @Override
    public Members save(Members members) {
        String sql = "insert into member(email, password, address, phoneNumber, name) values(?, ?, ?, ?, ?)"; // 쿼리문
        Connection conn = null;
        PreparedStatement pstmt = null; // SQL 문을 데이터베이스에 보내기 위한 객체
        ResultSet rs = null; // 결과 값을 받는 객체
        /**
         * try-catch-finally close()
         */
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, // PreparedStatement 객체 생성, 객체 생성시 SQL 문장 저장
                    Statement.RETURN_GENERATED_KEYS); // DB 에서 새로 생성된 id 값을 읽어오는 기능
            pstmt.setString(1, members.getEmail()); // pstmt.set<DATA TYPE>(순서, 값)
            pstmt.setString(2, members.getPassword());
            pstmt.setString(3, members.getAddress());
            pstmt.setString(4, members.getPhone());
            pstmt.setString(5, members.getName());
            pstmt.executeUpdate(); // SQL 문장 실행 후, 변경된 row 수 int type 리턴, DB 에 실제 쿼리를 보낸다.
            // pstmt.excuteUpdate() : insert, update, delete
            // pstmt.excuteQuery() : select
            rs = pstmt.getGeneratedKeys(); // DB 에 AUTO_INCREMENT 로 인해 자동 생성된 key 를 가져오는 쿼리
            if (rs.next()) { // DB 가 생성한 값을 읽어서 Member 클래스에 넣어주는 과정
                members.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // Connection 사용 후 모든 리소스를 반환해야한다.
        }
    }

    @Override
    public Members update(Members members) {
        String sql = "update member set address = ?, phoneNumber = ? where id = ?  "; // 쿼리문
        /**
         * try-with-resources
         */
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, members.getAddress());
            pstmt.setString(2, members.getPhone());
            pstmt.setLong(3, members.getId());
            pstmt.executeUpdate();
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * id 로 조회
     */
    @Override
    public Optional<Members> findById(Long id) {
        String sql = "select * from member where id = ?";
        /**
         * try-with-resources
         */
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) { // indent-depth : 2
                if (rs.next()) { // 값이 있으면 멤버 객체를 생성 후
                    Members members = new Members();
                    members.setId(rs.getLong("id"));
                    members.setEmail(rs.getString("email"));
                    members.setPassword(rs.getString("password"));
                    members.setAddress(rs.getString("address"));
                    members.setPhone(rs.getString("phoneNumber"));
                    members.setName(rs.getString("name"));
                    members.setLevel(rs.getString("level"));
                    return Optional.of(members); // 멤버 객체 반환
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 모든 값 조회
     */
    @Override
    public List<Members> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Members> members = new ArrayList<>(); // Member 객체를 담는 List 컬렉션 생성
            while (rs.next()) {
                Members member = new Members();
                member.setId(rs.getLong("id"));
                member.setEmail(rs.getString("email"));
                member.setPassword(rs.getString("password"));
                member.setAddress(rs.getString("address"));
                member.setPhone(rs.getString("phoneNumber"));
                member.setName(rs.getString("name"));
                member.setLevel(rs.getString("level"));
                members.add(member); // 루프를 돌면서 List 컬렉션에 Member 객체를 담는다.
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /**
     * email 로 조회
     */
    @Override
    public Optional<Members> findByEmail(String email) {
        String sql = "select * from member where email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Members members = new Members();
                members.setId(rs.getLong("id"));
                members.setEmail(rs.getString("email"));
                members.setPassword(rs.getString("password"));
                members.setAddress(rs.getString("address"));
                members.setPhone(rs.getString("phoneNumber"));
                members.setName(rs.getString("name"));
                members.setLevel(rs.getString("level"));
                return Optional.of(members);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    } // Spring 을 통해서 Connection 을 얻어올땐 DataSourceUtils 를 사용해야한다.

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) { // 사용 순서와 반대로 close
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException { // Spring 에서는 릴리즈도 DataSourceUtils 사용
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
