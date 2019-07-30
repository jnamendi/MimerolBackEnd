package bmbsoft.orderfoodonline.entities;

public class CloseOpen {
    private String closeTime;
    private String openTime;
    private Long idRestaurantWork;

    public CloseOpen(String closeTime, String openTime, Long idRestaurantWork) {
        this.closeTime = closeTime;
        this.openTime = openTime;
        this.idRestaurantWork = idRestaurantWork;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Long getIdRestaurantWork() {
        return idRestaurantWork;
    }

    public void setIdRestaurantWork(Long idRestaurantWork) {
        this.idRestaurantWork = idRestaurantWork;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getOpenTime() {
        return openTime;
    }
}
