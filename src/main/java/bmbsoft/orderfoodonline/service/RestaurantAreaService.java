package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.RestaurantAreaDAO;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.RestaurantArea;
import bmbsoft.orderfoodonline.model.RestaurantDeliveryAreaModel;
import bmbsoft.orderfoodonline.model.RestaurantDeliveryDistrictModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantAreaService {
    @Autowired
    private RestaurantAreaDAO areaDAO;

    public List<RestaurantDeliveryAreaModel> getDistrictByRestaurant(long restaurantId) {
        List<RestaurantArea> ra = areaDAO.getDistrictIdByRestaurantId(restaurantId);
        List<RestaurantDeliveryAreaModel> deliveryAreaModelsList = new ArrayList<>();
        List<RestaurantDeliveryAreaModel> result = new ArrayList<>();
        Set<Long> cityList = new HashSet<>();

        // extract items
        if(!ra.isEmpty()) {
            for (RestaurantArea r: ra) {
                RestaurantDeliveryAreaModel model = new RestaurantDeliveryAreaModel();
                cityList.add(r.getDistrict().getCity().getCityId());

                List<RestaurantDeliveryDistrictModel> districtModelList = new ArrayList<>();

                RestaurantDeliveryDistrictModel districtModel = new RestaurantDeliveryDistrictModel();

                districtModel.setDistrictCode(r.getDistrict().getCode());
                districtModel.setDistrictId(r.getDistrict().getDistrictId());
                districtModel.setDistrictName(r.getDistrict().getName());
                districtModel.setStatus(r.getDistrict().getStatus());

                districtModelList.add(districtModel);

                model.setDistricts(districtModelList);
                model.setCityId(r.getDistrict().getCity().getCityId());
                model.setCityCode(r.getDistrict().getCity().getCityCode());
                model.setStatus(r.getDistrict().getCity().getStatus());
                model.setCityId(r.getDistrict().getCity().getCityId());

                deliveryAreaModelsList.add(model);
            }
        }

        // merge item
        if (!cityList.isEmpty()) {
            for (Long c : cityList) {
                RestaurantDeliveryAreaModel rs = new RestaurantDeliveryAreaModel();
                List<RestaurantDeliveryDistrictModel> districtModelList = new ArrayList<>();
                for (RestaurantDeliveryAreaModel md : deliveryAreaModelsList) {
                    if(md.getCityId().equals(c)) {
                        rs.setCityId(md.getCityId());
                        rs.setCityCode(md.getCityCode());
                        rs.setStatus(md.getStatus());
                    }
                    districtModelList.add(md.getDistricts().get(0));
                }
                rs.setDistricts(districtModelList);
                result.add(rs);
            }

        }
        return result;
    }
}
