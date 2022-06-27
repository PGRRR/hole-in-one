package nosleepcoders.holeinonejdbc.repository;

import nosleepcoders.holeinonejdbc.domain.Orders;
import nosleepcoders.holeinonejdbc.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public JdbcOrderRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public Orders save(Orders orders) {
        String sql = "insert into orders(number, total_price, member_id, golfInfo_id) values (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
             ) {
            pstmt.setString(1, orders.getNumber());
            pstmt.setString(2, orders.getTotal_price());
            pstmt.setLong(3, orders.getMember_id());
            pstmt.setLong(4, orders.getGolfInfo_id());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    orders.setId(rs.getLong(1));
                } else {
                    throw new SQLException("id 조회 실패");
                }
                return orders;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String delete(String number) {
        String sql = "delete from orders where number = ?";
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
    public Optional<Orders> findById(Long id) {
        String sql = "select * from orders where id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Orders orders = new Orders();
                    orders.setId(rs.getLong("id"));
                    orders.setNumber(rs.getString("number"));
                    orders.setTotal_price(rs.getString("total_price"));
                    orders.setMember_id(rs.getLong("member_id"));
                    return Optional.of(orders);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Orders> findByNumber(String number) {
        String sql = "select * from orders where number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Orders orders = new Orders();
                    orders.setId(rs.getLong("id"));
                    orders.setNumber(rs.getString("number"));
                    orders.setTotal_price(rs.getString("total_price"));
                    orders.setMember_id(rs.getLong("member_id"));
                    return Optional.of(orders);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Orders> findNumberByMemberId(Long id) {
        String sql = "select * from orders left join golfInfo on orders.golfInfo_id = golfInfo.id where member_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Orders> orders = new ArrayList<>();
                while (rs.next()) {
                    Orders order = new Orders();
                    order.setNumber(rs.getString("number"));
                    order.setGolfInfo_id(rs.getLong("golfInfo_id"));
                    orders.add(order);
                }
                return orders;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Store> findStoreByMemberId(Long id) {
        String sql = "select * from reservations left join stores on reservations.store_id = store.store_id where member_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Store> stores = new ArrayList<>();
                while (rs.next()) {
                    Store store = new Store();
                    store.setStore_id(rs.getLong("store_id"));
                    store.setStore_name(rs.getString("store_name"));
                    stores.add(store);
                }
                return stores;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
