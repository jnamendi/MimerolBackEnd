package bmbsoft.orderfoodonline.entities;

public class CloseOpen {
    private String closeTime;
    private String opewnTime;
    private Long idRestaurantWork;

    public CloseOpen(String closeTime, String opewnTime, Long idRestaurantWork) {
        this.closeTime = closeTime;
        this.opewnTime = opewnTime;
        this.idRestaurantWork = idRestaurantWork;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setOpewnTime(String opewnTime) {
        this.opewnTime = opewnTime;
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

    public String getOpewnTime() {
        return opewnTime;
    }
}
