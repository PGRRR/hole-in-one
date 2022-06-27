package nosleepcoders.holeinonejdbc.domain;

/**
 * 예약 도메인 객체
 */
public class Orders {
    private Long id;
    private String number;
    private String total_price;
    private Long member_id;
    private Long golfInfo_id;

    public Long getGolfInfo_id() {
        return golfInfo_id;
    }

    public void setGolfInfo_id(Long golfInfo_id) {
        this.golfInfo_id = golfInfo_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }
}
