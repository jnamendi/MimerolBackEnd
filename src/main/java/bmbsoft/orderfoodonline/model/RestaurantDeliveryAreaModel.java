package bmbsoft.orderfoodonline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantDeliveryAreaModel {
    private Long cityId;
    private String cityCode;
    private Integer status;
    private List<RestaurantDeliveryDistrictModel> restaurantDeliveryDistrictModel;

    public RestaurantDeliveryAreaModel() {
    }

    public RestaurantDeliveryAreaModel(Long cityId, String cityCode, Integer status, List<RestaurantDeliveryDistrictModel> restaurantDeliveryDistrictModel) {
        this.cityId = cityId;
        this.cityCode = cityCode;
        this.status = status;
        this.restaurantDeliveryDistrictModel = restaurantDeliveryDistrictModel;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RestaurantDeliveryDistrictModel> getRestaurantDeliveryDistrictModel() {
        return restaurantDeliveryDistrictModel;
    }

    public void setRestaurantDeliveryDistrictModel(List<RestaurantDeliveryDistrictModel> restaurantDeliveryDistrictModel) {
        this.restaurantDeliveryDistrictModel = restaurantDeliveryDistrictModel;
    }
}
