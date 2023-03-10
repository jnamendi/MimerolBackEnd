package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.RestaurantAreaDAO;
import bmbsoft.orderfoodonline.dao.RestaurantDeliveryCostDAO;
import bmbsoft.orderfoodonline.entities.City;
import bmbsoft.orderfoodonline.entities.District;
import bmbsoft.orderfoodonline.entities.RestaurantArea;
import bmbsoft.orderfoodonline.model.*;
import bmbsoft.orderfoodonline.model.shared.RestaurantDeliveryCostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RestaurantAreaService {
    @Autowired
    private RestaurantAreaDAO areaDAO;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RestaurantAreaService restaurantAreaService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private RestaurantDeliveryCostService restaurantDeliveryCostService;

    @Transactional
    public List<RestaurantDeliveryAreaModel> getDistrictByRestaurant(long restaurantId) {
        List<RestaurantArea> ra = areaDAO.getZoneIdByRestaurantId(restaurantId);
        List<RestaurantDeliveryAreaModel> deliveryAreaModelsList = new ArrayList<>();
        List<RestaurantDeliveryAreaModel> result = new ArrayList<>();
        Set<Long> cityList = new HashSet<>();

        // extract items
        if(ra != null && !ra.isEmpty()) {
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

    @Transactional
    public List<CityViewModel> getCityByRestaurant(long restaurantId) {
        List<RestaurantArea> ra = areaDAO.getZoneIdByRestaurantId(restaurantId);
        List<CityViewModel> cityViewModelList = new ArrayList<>();
        Set<Long> cityList = new HashSet<>();

        if(ra != null && !ra.isEmpty()) {
            for(RestaurantArea r : ra) {
                cityList.add(r.getDistrict().getCity().getCityId());
            }

            if(!cityList.isEmpty()) {
                for (Long city: cityList) {
                    for (RestaurantArea r: ra) {
                        if(r.getDistrict().getCity().getCityId().equals(city)) {
                            CityViewModel model = new CityViewModel();
                            model.setCityId(r.getDistrict().getCity().getCityId());
                            model.setCountry(countryService.convertEntityToModel(r.getDistrict().getCity().getCountry()));
                            model.setName(r.getDistrict().getCity().getCityName());
                            model.setCode(r.getDistrict().getCity().getCityCode());
                            model.setCreatedBy(r.getDistrict().getCity().getCreatedBy());
                            model.setCreateDate(r.getDistrict().getCity().getCreatedDate());
                            model.setStatus(r.getDistrict().getCity().getStatus());

                            cityViewModelList.add(model);
                            break;
                        }
                    }
                }
            }
        }

        return cityViewModelList;
    }

    @Transactional
    public List<DistrictViewModel> getDistrictByRestaurantAndCity(long restaurantId, long cityId) {
        List<DistrictViewModel> districtViewModels = new ArrayList<>();
        List<DistrictViewModel> districtViewModels2 = districtService.getAllByCityId(cityId);
        List<Long> arrayDis = restaurantAreaService.getDistrictDeliveryListByRestaurant(restaurantId);
        if(arrayDis != null && !arrayDis.isEmpty() && districtViewModels2 != null && !districtViewModels2.isEmpty()){
            arrayDis.forEach(d->{
                districtViewModels2.forEach( d2 -> {
                    if(d == d2.getDistrictId()){
                        districtViewModels.add(districtService.getById(d));
                    }
                });
            });
        }
        districtViewModels.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        return districtViewModels;
    }


    @Transactional
    public List<Long> getDistrictDeliveryListByRestaurant(long restaurantId) {
        Set<Long> districts = new HashSet<>();
        List<RestaurantArea> ra = areaDAO.getZoneIdByRestaurantId(restaurantId);
        if(ra != null && !ra.isEmpty()) {
            for (RestaurantArea r: ra) {
                districts.add(r.getDistrict().getDistrictId());
            }
        }

        return new ArrayList(districts);
    }

    @Transactional
    public List<DeliveryArea> getDeliveryZone(long restaurantId, List<Long> idDis){
        List<DeliveryArea> deliveryAreaList = new ArrayList<>();
        List<RestaurantArea> ra = areaDAO.getZoneIdByRestaurantId(restaurantId);
        List<RestaurantDeliveryCostModel> restaurantDeliveryCostModelList = restaurantDeliveryCostService.getDeliveryByRestaurant(restaurantId);
        if(ra != null && !ra.isEmpty() && idDis!= null && !idDis.isEmpty()) {
            for (Long l: idDis ){
                DeliveryArea dlv = new DeliveryArea();
                List<Long> z = new ArrayList<>();
                dlv.setDeliveryAreaId(l);
                if(restaurantDeliveryCostModelList != null && !restaurantDeliveryCostModelList.isEmpty()){
                    for(RestaurantDeliveryCostModel cost : restaurantDeliveryCostModelList){
                        if(cost.getDistrict() == l){
                            dlv.setDeliveryCost(cost.getDeliveryCost());
                        }
                    }
                }
                for (RestaurantArea r: ra) {
                    if(r.getDistrict().getDistrictId().equals(l) && r.getZone().getZoneId() != null){
                        z.add(r.getZone().getZoneId());
                    }
                }
                dlv.setDeliveryZoneId(z);
                deliveryAreaList.add(dlv);
            }
        }
        return deliveryAreaList;
    }

    @Transactional
    public boolean getZoneByRestaurant(long zoneId,long restaurantId){
        return areaDAO.checkZone(zoneId,restaurantId);
    }

    private DistrictViewModel convertEntityToModel(final District district) {
        if (null == district)
            return null;
        DistrictViewModel model = new DistrictViewModel();
        model.setDistrictId(district.getDistrictId());
        model.setCity(cityService.convertEntityToModel(district.getCity()));
        model.setName(district.getName());
        model.setCode(district.getCode());
        model.setCreatedBy(district.getCreatedBy());
        model.setStatus(district.getStatus());
        return model;
    }
}
