package bmbsoft.orderfoodonline.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_restaurant")
public class RestaurantArea implements java.io.Serializable {
    private Long restaurantAreaId;
    private Restaurant restaurant;
    private District district;

    public RestaurantArea() {
    }

    public RestaurantArea(Long restaurantAreaId, Restaurant restaurant, District district) {
        this.restaurantAreaId = restaurantAreaId;
        this.restaurant = restaurant;
        this.district = district;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "restaurant_area_id", unique = true, nullable = false)
    public Long getRestaurantAreaId() {
        return restaurantAreaId;
    }

    public void setRestaurantAreaId(Long restaurantAreaId) {
        this.restaurantAreaId = restaurantAreaId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
