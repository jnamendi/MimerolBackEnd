package bmbsoft.orderfoodonline.model.shared;

import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.DistrictViewModel;

public class RestaurantDeliveryCostResponse {
    private Long restaurantDeliveryCostId;
    private Restaurant restaurant;
    private Double deliveryCost;
    private DistrictViewModel district;

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

    public DistrictViewModel getDistrict() {
        return district;
    }

    public void setDistrict(DistrictViewModel district) {
        this.district = district;
    }
}
