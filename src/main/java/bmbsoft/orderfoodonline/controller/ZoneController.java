package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.model.ZoneViewModel;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.ZoneService;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ZoneController {
    public static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    @Autowired
    ZoneService zoneService;

    @RequestMapping(value = "/api/zone/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        ResponseGet responseGet = new ResponseGet();
        try {
            List<ZoneViewModel> c = zoneService.getAll();
            return setContent(responseGet, c);
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseGet.setStatus(1);
            responseGet.setMessage(e.toString());
            return new ResponseEntity<>(responseGet, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/zone/getByDistrict/{districtId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllByDistrict(@PathVariable long districtId) {
        ResponseGet responseGet = new ResponseGet();
        try {
            List<ZoneViewModel> c = zoneService.getByDistrict(districtId);
            return setContent(responseGet, c);
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseGet.setStatus(1);
            responseGet.setMessage(e.toString());
            return new ResponseEntity<>(responseGet, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/zone/getZoneByDistrict/{districtId}/{restaurantId}", method = RequestMethod.GET)
    public ResponseEntity<?> getZoneByDistrict(@PathVariable long districtId,@PathVariable long restaurantId) {
        ResponseGet responseGet = new ResponseGet();
        try {
            List<ZoneViewModel> c = zoneService.getZoneByDistrict(districtId,restaurantId);
            return setContent(responseGet, c);
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseGet.setStatus(1);
            responseGet.setMessage(e.toString());
            return new ResponseEntity<>(responseGet, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/zone/checkZoneRestaurant/{zoneId}/{restaurantId}", method = RequestMethod.GET)
    public Boolean checkZoneRestaurant(@PathVariable long zoneId, @PathVariable long restaurantId) {
        ResponseGet responseGet = new ResponseGet();
        try {
             return  zoneService.checkZoneByRestaurant(zoneId,restaurantId);
        } catch (Exception e) {
           return  false;
        }
    }

    private ResponseEntity<?> setContent(ResponseGet responseGet, List<ZoneViewModel> c) {
        if (c == null || c.isEmpty()) {
            responseGet.setStatus(0);
            responseGet.setMessage("Could not found items.");
            responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
            return new ResponseEntity<>(responseGet, HttpStatus.OK);
        }
        responseGet.setStatus(0);
        responseGet.setContent(c);
        return new ResponseEntity<>(responseGet, HttpStatus.OK);
    }

}
