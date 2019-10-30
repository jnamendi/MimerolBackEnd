package bmbsoft.orderfoodonline.entities;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "restaurant_delivery_cost")
public class RestaurantDeliveryCost implements java.io.Serializable{
    private Long restaurantDeliveryCostId;
    private Restaurant restaurant;
    private Double deliveryCost;
    private Long districtId;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;

    public RestaurantDeliveryCost() {
    }

    public RestaurantDeliveryCost(Long restaurantDeliveryCostId, Restaurant restaurant, Double deliveryCost, Long district, Date createdDate, String createdBy, Date modifiedDate, String modifiedBy) {
        this.restaurantDeliveryCostId = restaurantDeliveryCostId;
        this.restaurant = restaurant;
        this.deliveryCost = deliveryCost;
        this.districtId = district;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "restaurant_delivery_cost_id", unique = true, nullable = false)
    public Long getRestaurantDeliveryCostId() {
        return restaurantDeliveryCostId;
    }

    public void setRestaurantDeliveryCostId(Long restaurantDeliveryCostId) {
        this.restaurantDeliveryCostId = restaurantDeliveryCostId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Column(name = "delivery_cost", precision = 10, scale = 2)
    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

//    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "district_id", nullable = false)
    public Long getDistrict() {
        return districtId;
    }

    public void setDistrict(Long district) {
        this.districtId = district;
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
