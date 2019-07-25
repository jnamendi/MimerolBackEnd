package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.RestaurantWorkTimeDAO;
import bmbsoft.orderfoodonline.entities.RestaurantWorkTime;
import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RestaurantWorkTimeService {
    public static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private RestaurantWorkTimeDAO restaurantWorkTimeDAO;

    public RestaurantWorkTime findById(final long id) {
        return restaurantWorkTimeDAO.findById(id);
    }

    public Boolean save(RestaurantWorkTime restaurantWorkTime) {
        return restaurantWorkTimeDAO.save(restaurantWorkTime);
    }

    public Boolean deleteByRestaurantId(long resId) {
        return restaurantWorkTimeDAO.deleteByRestaurantId(resId);
    }

    public Boolean deleteById(long id) {
        return restaurantWorkTimeDAO.delete(id);
    }

    public List<RestaurantWorkTimeModel> getByRestaurantId(long resId) {
        List<RestaurantWorkTime> list = restaurantWorkTimeDAO.getWorkTimeByRestaurant(resId);
        List<RestaurantWorkTimeModel> results = new LinkedList<>();
        if(!list.isEmpty()) {
            for (RestaurantWorkTime rs : list) {
                RestaurantWorkTimeModel rwt = new RestaurantWorkTimeModel();
                rwt.setRestaurantWorkTimeId(rs.getResWorkTimeId());
                rwt.setRestaurantId(resId);
                rwt.setCloseTime(rs.getEndTime());
                rwt.setOpenTime(rs.getStartTime());
                rwt.setWeekDay(rs.getWeekday());
                results.add(rwt);
            }
        }
        return results;
    }

}
