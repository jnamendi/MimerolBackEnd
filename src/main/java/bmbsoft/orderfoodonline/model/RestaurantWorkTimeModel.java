package bmbsoft.orderfoodonline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantWorkTimeModel {

    private Long restaurantWorkTimeId;
    private Long restaurantId;

    private String weekDay;
    private String openTime;
    private String closeTime;

    public Long getRestaurantWorkTimeId() {
        return restaurantWorkTimeId;
    }

    public void setRestaurantWorkTimeId(Long restaurantWorkTimeId) {
        this.restaurantWorkTimeId = restaurantWorkTimeId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
