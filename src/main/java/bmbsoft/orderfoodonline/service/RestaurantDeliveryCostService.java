package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.DistrictDAO;
import bmbsoft.orderfoodonline.dao.RestaurantDeliveryCostDAO;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.RestaurantDeliveryCost;
import bmbsoft.orderfoodonline.model.DistrictViewModel;
import bmbsoft.orderfoodonline.model.shared.RestaurantDeliveryCostModel;
import bmbsoft.orderfoodonline.model.shared.RestaurantDeliveryCostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantDeliveryCostService {
    @Autowired
    private RestaurantDeliveryCostDAO restaurantDeliveryCostDAO;

    @Autowired
    private DistrictDAO districtDAO;

    public RestaurantDeliveryCost findById(final long id) {
        return restaurantDeliveryCostDAO.findById(id);
    }

    public Boolean save(RestaurantDeliveryCost restaurantDeliveryCost) {
        return restaurantDeliveryCostDAO.save(restaurantDeliveryCost);
    }

    public Boolean deleteByRestaurantId(long resId) {
        return restaurantDeliveryCostDAO.deleteByRestaurantId(resId);
    }

    public Boolean deleteById(long id) {
        return restaurantDeliveryCostDAO.delete(id);
    }

    public List<RestaurantDeliveryCostModel> getDeliveryByRestaurant(Long idRes){
        List<RestaurantDeliveryCost> restaurantDeliveryCostList = restaurantDeliveryCostDAO.getDeliveryCostByRestaurant(idRes);
        return convertDeliveryCost(restaurantDeliveryCostList);
    }

    private List<RestaurantDeliveryCostModel> convertDeliveryCost(List<RestaurantDeliveryCost> list){
        if(list != null && !list.isEmpty()){
            List<RestaurantDeliveryCostModel> result = new ArrayList<>();
            for(RestaurantDeliveryCost cost : list){
                RestaurantDeliveryCostModel restaurantDeliveryCostModel = new RestaurantDeliveryCostModel();
                restaurantDeliveryCostModel.setRestaurantDeliveryCostId(cost.getRestaurantDeliveryCostId());
                restaurantDeliveryCostModel.setDistrict(cost.getDistrict());
                restaurantDeliveryCostModel.setDeliveryCost(cost.getDeliveryCost());
                result.add(restaurantDeliveryCostModel);
            }
            return result;
        }else{
            return null;
        }
    }
    @Transactional
    public List<RestaurantDeliveryCostResponse> getDeliveryByRestaurantId(Long idRes){
        List<RestaurantDeliveryCost> restaurantDeliveryCostList = restaurantDeliveryCostDAO.getDeliveryCostByRestaurant(idRes);
        return convertDeliveryCost2(restaurantDeliveryCostList);
    }

    private List<RestaurantDeliveryCostResponse> convertDeliveryCost2(List<RestaurantDeliveryCost> list){
        if(list != null && !list.isEmpty()){
            List<RestaurantDeliveryCostResponse> result = new ArrayList<>();
            for(RestaurantDeliveryCost cost : list){
                RestaurantDeliveryCostResponse restaurantDeliveryCostModel = new RestaurantDeliveryCostResponse();
                restaurantDeliveryCostModel.setRestaurantDeliveryCostId(cost.getRestaurantDeliveryCostId());
                District d = districtDAO.getById(cost.getDistrict());
                DistrictViewModel d2 = new DistrictViewModel();
                d2.setName(d.getName());
                d2.setCode(d.getCode());
                d2.setDistrictId(d.getDistrictId());
                restaurantDeliveryCostModel.setDistrict(d2);
                restaurantDeliveryCostModel.setDeliveryCost(cost.getDeliveryCost());
                result.add(restaurantDeliveryCostModel);
            }
            return result;
        }else{
            return null;
        }
    }

}
