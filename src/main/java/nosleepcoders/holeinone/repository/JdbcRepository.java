package nosleepcoders.holeinone.repository;

import nosleepcoders.holeinone.domain.Member;

import java.sql.*;

public class JdbcRepository {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
        dbConnection.insert("INSERT INTO member(id, email, password, name, level) values(?, ?, ?, ?, ?)");
        dbConnection.findByUserEmail("pgrrr1@gmail.com");
        dbConnection.close();
    }
}

class DBConnection {
    Connection connection;
    private static long sequence = 3L;

    public void connect() {
        String url = "jdbc:mysql://localhost:3306/spring?serverTimezone=UTC";
        String user = "root";
        String password = "12345678";
        String driverName = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("로드 오류 " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("연결 오류 " + e.getMessage());
        }
    }

    public void insert(String sql) {
        PreparedStatement pstmt = null; // SQL 문을 데이터베이스에 보내기 위한 객체
        String SQL = sql; // SQL 문

        try {
            pstmt = connection.prepareStatement(SQL); // PreparedStatement 객체 생성, 객체 생성시 SQL 문장 저장

            // pstmt.set<DATA TYPE>(순서, 값)
            pstmt.setLong(1, ++sequence);
            pstmt.setString(2, "pgrrr1@gmail.com");
            pstmt.setString(3, "1234");
            pstmt.setString(4, "pgrrr");
            pstmt.setString(5, "0");

            // SQL 문장을 실행하고 결과를 리턴
            // SQL 문장 실행 후, 변경된 row 수 int type 리턴
            int r = pstmt.executeUpdate();
            // pstmt.excuteQuery() : select
            // pstmt.excuteUpdate() : insert, update, delete

            System.out.println("변경된 row : " + r);
        } catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        } finally {
            // 사용 순서와 반대로 close
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Member findByUserEmail(String email) {
        PreparedStatement pstmt = null;
        String SQL = "SELECT * FROM member where email = ?";
        try {
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name")
                );
                return member;
            }

        } catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        } finally {
            // 사용 순서와 반대로 close
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Error : " + e.getMessage());
        }
    }
}