package bmbsoft.orderfoodonline.service.fcm;

import bmbsoft.orderfoodonline.dao.UserFCMDAO;
import bmbsoft.orderfoodonline.entities.UserFCM;
import bmbsoft.orderfoodonline.model.UserFCMModel;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserFCMService {

    @Autowired
    private UserFCMDAO userFCMDAO;

    @Autowired
    private FCMService fcmService;

    @Transactional
    public void updateToken(UserFCMModel userFCMModel) {
        if(userFCMModel != null) {
            UserFCM userFCM = userFCMDAO.getByUserIdAndDeviceId(userFCMModel.getUserId(), userFCMModel.getDeviceId());
            if(userFCM != null) {
                userFCM.setToken(userFCMModel.getToken());
                userFCMDAO.save(userFCM);
            } else {
                UserFCM user = new UserFCM();
                user.setDeviceId(userFCMModel.getDeviceId());
                user.setUserId(userFCMModel.getUserId());
                user.setToken(userFCMModel.getToken());
                userFCMDAO.save(user);
            }
        }
    }

    public List<UserFCMModel> getListTokenByListUserIds(List<Long> userIds) {
        List<UserFCMModel> list = new LinkedList<>();
        if(userIds != null && !userIds.isEmpty()) {
            for(Long id : userIds) {
                convertEntityToModel(list, id);
            }
        }
        return list;
    }

    public List<UserFCMModel> getListTokenByUserId(Long userIds) {
        List<UserFCMModel> list = new LinkedList<>();
        if (userIds != null) {
            convertEntityToModel(list, userIds);
        }
        return list;
    }

    public void pushNotificationToUsersWithoutTopic(String title, String message, List<UserFCMModel> userFCMS) {
        if (!userFCMS.isEmpty()) {
            try {
                List<String> listToken = new ArrayList<>();
                for (UserFCMModel userFCM : userFCMS) {
                    listToken.add(userFCM.getToken());
                }
                fcmService.sendMulticast(title, message, listToken);
            } catch (FirebaseMessagingException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void convertEntityToModel(List<UserFCMModel> list, Long id) {
        List<UserFCM> userFCMList = userFCMDAO.getByUser(id);
        if(userFCMList != null && !userFCMList.isEmpty()) {
            for (UserFCM userFCM : userFCMList) {
                UserFCMModel model = new UserFCMModel();
                model.setUserId(userFCM.getUserId());
                model.setDeviceId(userFCM.getDeviceId());
                model.setToken(userFCM.getToken());
                list.add(model);
            }
        }
    }
}
