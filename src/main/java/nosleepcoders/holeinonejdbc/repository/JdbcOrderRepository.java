package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.GolfInfo;
import nosleepcoders.holeinonejdbc.domain.Order;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * OrderRepository 인터페이스의 JDBC 구현체
 */
@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final DataSource dataSource;

    public JdbcOrderRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Order save(Order order) {
        String sql = "insert into orders(number, total_price, user_id) values (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet rs = pstmt.getGeneratedKeys()) {
            pstmt.setString(1, order.getNumber());
            pstmt.setString(2, order.getTotal_price());
            pstmt.setLong(3, order.getMember_id());
            pstmt.executeUpdate();
            if (rs.next()) {
                order.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return order;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String delete(String number) {
        String sql = "delete form orders where number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
            return number;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        String sql = "select * from orders where id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setNumber(rs.getString("number"));
                    order.setTotal_price(rs.getString("total_price"));
                    order.setMember_id(rs.getLong("user_id"));
                    return Optional.of(order);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Order> findByNumber(String number) {
        String sql = "select * from orders where number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setNumber(rs.getString("number"));
                    order.setTotal_price(rs.getString("total_price"));
                    order.setMember_id(rs.getLong("user_id"));
                    return Optional.of(order);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Order> findNumberByMemberId(Long id) {
        String sql = "select * from orders left join golfInfo on orders.golfInfo_id = golfInfo.id where member_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order order = new Order();
                    order.setNumber(rs.getString("Number"));
                    orders.add(order);
                }
                return orders;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public List<GolfInfo> findStoreByMemberId(Long id) {
        String sql = "select * from orders left join golfInfo on orders.golfInfo_id = golfInfo.id where member_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<GolfInfo> golfInfos = new ArrayList<>();
                while (rs.next()) {
                    GolfInfo golfInfo = new GolfInfo();
                    golfInfo.setId(rs.getLong("id"));
                    golfInfo.setGolfName(rs.getString("golfName"));
                    golfInfos.add(golfInfo);
                }
                return golfInfos;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
