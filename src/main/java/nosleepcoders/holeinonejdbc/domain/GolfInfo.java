package nosleepcoders.holeinonejdbc.domain;

/**
 * 매장 도메인 객체
 */
public class GolfInfo {
    private Long id;
    private String golfName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGolfName() {
        return golfName;
    }

    public void setGolfName(String golfName) {
        this.golfName = golfName;
    }
}

