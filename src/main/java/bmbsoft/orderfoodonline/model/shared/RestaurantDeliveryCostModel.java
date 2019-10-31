package bmbsoft.orderfoodonline.model.shared;

import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.Restaurant;

import java.util.Date;

public class RestaurantDeliveryCostModel {
    private Long restaurantDeliveryCostId;
    private Restaurant restaurant;
    private Double deliveryCost;
    private Long district;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;

    public RestaurantDeliveryCostModel() {
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public Long getRestaurantDeliveryCostId() {
        return restaurantDeliveryCostId;
    }

    public void setRestaurantDeliveryCostId(Long restaurantDeliveryCostId) {
        this.restaurantDeliveryCostId = restaurantDeliveryCostId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }



    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
