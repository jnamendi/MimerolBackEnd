package bmbsoft.orderfoodonline.controller;

import bmbsoft.orderfoodonline.model.UserFCMModel;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.fcm.UserFCMService;
import bmbsoft.orderfoodonline.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserFCMController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(UserFCMController.class);

    @Autowired
    private UserFCMService userFCMService;

    @RequestMapping(value = "/api/updateTokenFCM", method = RequestMethod.POST)
    public ResponseEntity<?> updateToken(UserFCMModel userFCMModel) {
        ResponseGet rs = new ResponseGet();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        try {
            userFCMService.updateToken(userFCMModel);

            rs.setStatus(0);
            rs.setMessage("OK");
            rs.setErrorType(Constant.ErrorTypeCommon.OK);
            httpStatus = HttpStatus.OK;

            return new ResponseEntity<>(rs, httpStatus);
        } catch (Exception e) {
            logger.error(e.toString());
            rs.setStatus(1);
            rs.setMessage(e.toString());
            rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
        }

        return new ResponseEntity<>(rs, httpStatus);
    }
}
