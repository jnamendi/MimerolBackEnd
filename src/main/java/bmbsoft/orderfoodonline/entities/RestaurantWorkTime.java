package bmbsoft.orderfoodonline.entities;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "restaurant_work_time")
public class RestaurantWorkTime {

    private Long resWorkTimeId;
    private Restaurant restaurant;
    private String weekday;
    private String startTime;
    private String endTime;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;

    public RestaurantWorkTime() {
    }

    public RestaurantWorkTime(String weekday) {
        this.weekday = weekday;
    }

    public RestaurantWorkTime(Long resWorkTimeId, Restaurant restaurant, String weekday, String startTime, String endTime, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy) {
        this.resWorkTimeId = resWorkTimeId;
        this.restaurant = restaurant;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "res_work_time_id", unique = true, nullable = false)
    public Long getResWorkTimeId() {
        return resWorkTimeId;
    }

    public void setResWorkTimeId(Long resWorkTimeId) {
        this.resWorkTimeId = resWorkTimeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Column(name = "weekday")
    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @Column(name = "open_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column(name = "close_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", length = 19)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "modified_by")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
