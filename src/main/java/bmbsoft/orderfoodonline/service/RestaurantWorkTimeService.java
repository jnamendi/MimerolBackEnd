package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.RestaurantWorkTimeDAO;
import bmbsoft.orderfoodonline.entities.CloseOpen;
import bmbsoft.orderfoodonline.entities.RestaurantWorkTime;
import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ArrayList<RestaurantWorkTimeModel> wt = new ArrayList<>();
        if(!list.isEmpty()) {
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.MON.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.TUE.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.WED.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.THU.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.FRI.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.SAT.toString()));
            wt.add( new RestaurantWorkTimeModel(Constant.Weekday.SUN.toString()));
            int size = list.size();
            for (int i = 0 ; i < size ; i++){
                switch (list.get(i).getWeekday()){
                    case "MON":
                        wt.get(0).setRestaurantId(resId);
                        wt.get(0).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "TUE":
                        wt.get(1).setRestaurantId(resId);
                        wt.get(1).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "WED":
                        wt.get(2).setRestaurantId(resId);
                        wt.get(2).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "THU":
                        wt.get(3).setRestaurantId(resId);
                        wt.get(3).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "FRI":
                        wt.get(4).setRestaurantId(resId);
                        wt.get(4).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "SAT":
                        wt.get(5).setRestaurantId(resId);
                        wt.get(5).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                    case "SUN":
                        wt.get(6).setRestaurantId(resId);
                        wt.get(6).getList().add(new CloseOpen(list.get(i).getStartTime(),list.get(i).getEndTime(),list.get(i).getResWorkTimeId()));
                        break;
                }
            }
        }
        return wt;
    }

}
