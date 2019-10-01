package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.ZoneDAO;
import bmbsoft.orderfoodonline.entities.RestaurantArea;
import bmbsoft.orderfoodonline.entities.Zone;
import bmbsoft.orderfoodonline.model.DeliveryArea;
import bmbsoft.orderfoodonline.model.ZoneViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ZoneService {
    public static final Logger logger = LoggerFactory.getLogger(ZoneService.class);

    @Autowired
    private ZoneDAO zoneDAO;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private RestaurantAreaService restaurantAreaService;

    @Transactional
    public List<ZoneViewModel> getAll() {
        List<ZoneViewModel> listModel = new LinkedList<>();
        List<Zone> zones = zoneDAO.getAll();

        zones.forEach(zone -> {
            listModel.add(convertEntityToModel(zone));
        });
        return listModel;
    }

    @Transactional
    public Zone getBaseById(final long id) {
        return zoneDAO.getById(id);
    }

    @Transactional
    public ZoneViewModel getById(final long id) {
        return convertEntityToModel(zoneDAO.getById(id));
    }

    @Transactional
    public List<ZoneViewModel> getByDistrict(final long id) {
        List<ZoneViewModel> listModel = new LinkedList<>();
        List<Zone> zones = zoneDAO.getZoneByDistrictId(id);
        zones.forEach(zone -> {
            listModel.add(convertEntityToModel(zone));
        });
        return listModel;
    }

    @Transactional
    public List<ZoneViewModel> getZoneByDistrict(final  long idDis, long idRes){
        List<ZoneViewModel> listModel = new LinkedList<>();
        List<DeliveryArea> zoneList = restaurantAreaService.getDeliveryZone(idRes,restaurantAreaService.getDistrictDeliveryListByRestaurant(idRes));
        if(zoneList != null && !zoneList.isEmpty()){
            zoneList.forEach( s ->{
                if(s.getDeliveryAreaId() == idDis){
                    s.getDeliveryZoneId().forEach(z -> {
                        listModel.add(convertEntityToModel(zoneDAO.getById(z)));
                    });
                }
            });
        }
        return listModel;
    }

    @Transactional
    public boolean checkZoneByRestaurant(final  long zoneId, long idRes){
        return restaurantAreaService.getZoneByRestaurant(zoneId,idRes);
    }



    private ZoneViewModel convertEntityToModel(final Zone zone) {
        ZoneViewModel model = new ZoneViewModel();
        model.setZoneId(zone.getZoneId());
        model.setDistrict(districtService.convertEntityToModel(zone.getDistrict()));
        model.setName(zone.getName());
        model.setCode(zone.getCode());
        model.setCreatedBy(zone.getCreatedBy());
        model.setStatus(zone.getStatus());
        return model;
    }
}
