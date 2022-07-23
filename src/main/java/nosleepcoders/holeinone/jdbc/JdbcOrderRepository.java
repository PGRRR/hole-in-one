//package nosleepcoders.holeinone.repository;
//
//import nosleepcoders.holeinone.domain.Reservation;
//import nosleepcoders.holeinone.domain.Store;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.datasource.DataSourceUtils;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
///**
// * OrderRepository 인터페이스의 JDBC 구현체
// */
//@Repository
//public class JdbcOrderRepository implements OrderRepository {
//
//    private final DataSource dataSource;
//    @Autowired
//    public JdbcOrderRepository(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    private Connection getConnection() {
//        return DataSourceUtils.getConnection(dataSource);
//    }
//
//    @Override
//    public Reservation save(Reservation reservation) {
//        String sql = "insert into orders(number, total_price, member_id, golfInfo_id) values (?, ?, ?, ?)";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
//             ) {
//            pstmt.setString(1, reservation.getNumber());
//            pstmt.setString(2, reservation.getTotal_price());
//            pstmt.setLong(3, reservation.getMember_id());
//            pstmt.setLong(4, reservation.getGolfInfo_id());
//            pstmt.executeUpdate();
//            try (ResultSet rs = pstmt.getGeneratedKeys()) {
//                if (rs.next()) {
//                    reservation.setReservation_id(rs.getLong(1));
//                } else {
//                    throw new SQLException("id 조회 실패");
//                }
//                return reservation;
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public String delete(String number) {
//        String sql = "delete from orders where number = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, number);
//            pstmt.executeUpdate();
//            return number;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public Optional<Reservation> findById(Long id) {
//        String sql = "select * from orders where id = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    Reservation reservation = new Reservation();
//                    reservation.setReservation_id(rs.getLong("id"));
//                    reservation.setNumber(rs.getString("number"));
//                    reservation.setTotal_price(rs.getString("total_price"));
//                    reservation.setMember_id(rs.getLong("member_id"));
//                    return Optional.of(reservation);
//                } else {
//                    return Optional.empty();
//                }
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public Optional<Reservation> findByNumber(String number) {
//        String sql = "select * from orders where number = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, number);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    Reservation reservation = new Reservation();
//                    reservation.setReservation_id(rs.getLong("id"));
//                    reservation.setNumber(rs.getString("number"));
//                    reservation.setTotal_price(rs.getString("total_price"));
//                    reservation.setMember_id(rs.getLong("member_id"));
//                    return Optional.of(reservation);
//                } else {
//                    return Optional.empty();
//                }
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    @Override
//    public List<Reservation> findNumberByMemberId(Long id) {
//        String sql = "select * from orders left join golfInfo on orders.golfInfo_id = golfInfo.id where member_id = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                List<Reservation> reservations = new ArrayList<>();
//                while (rs.next()) {
//                    Reservation reservation = new Reservation();
//                    reservation.setNumber(rs.getString("number"));
//                    reservation.setGolfInfo_id(rs.getLong("golfInfo_id"));
//                    reservations.add(reservation);
//                }
//                return reservations;
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    public List<Store> findStoreByMemberId(Long id) {
//        String sql = "select * from reservations left join stores on reservations.store_id = store.store_id where member_id = ?";
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setLong(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                List<Store> stores = new ArrayList<>();
//                while (rs.next()) {
//                    Store store = new Store();
//                    store.setStore_id(rs.getLong("store_id"));
//                    store.setStore_name(rs.getString("store_name"));
//                    stores.add(store);
//                }
//                return stores;
//            }
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//}
