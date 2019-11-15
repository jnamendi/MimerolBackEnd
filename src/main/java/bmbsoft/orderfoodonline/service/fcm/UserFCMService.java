package bmbsoft.orderfoodonline.service.fcm;

import bmbsoft.orderfoodonline.dao.UserFCMDAO;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.entities.UserFCM;
import bmbsoft.orderfoodonline.model.MessageFCMModel;
import bmbsoft.orderfoodonline.model.UserFCMModel;
import bmbsoft.orderfoodonline.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserFCMService {

    @Autowired
    private UserFCMDAO userFCMDAO;

    @Autowired
    private FCMService fcmService;

    @Autowired
    private UserService userService;

    @Transactional
    public void updateToken(UserFCMModel userFCMModel) {
        if(userFCMModel != null) {
            UserFCM userFCM = userFCMDAO.getByUserIdAndDeviceId(userFCMModel.getUserId(), userFCMModel.getDeviceId());
            if(userFCM != null) {
                userFCM.setToken(userFCMModel.getToken());
                userFCMDAO.save(userFCM);
            } else {
                UserFCM user = new UserFCM();
                User u = userService.findById(userFCMModel.getUserId());
                user.setDeviceId(userFCMModel.getDeviceId());
                user.setUser(u);
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

    public void pushNotificationToUsersWithoutTopic(Long orderId, String orderCode, MessageFCMModel messageFCMModel, List<UserFCMModel> userFCMS) {
        if (!userFCMS.isEmpty()) {
            try {
                List<String> listToken = new ArrayList<>();
                for (UserFCMModel userFCM : userFCMS) {
                    listToken.add(userFCM.getToken());
                }
                JSONObject content = new JSONObject();

                content.put("orderId", orderId);
                content.put("orderCode", orderCode);

                Map<String, String> data = new HashMap<>();
                data.put("type", "1");
                data.put("messageEn", messageFCMModel.getMessageEn());
                data.put("titleEn", messageFCMModel.getTitleEn());
                data.put("messageEs", messageFCMModel.getMessageEs());
                data.put("titleEs", messageFCMModel.getTitleEs());
                data.put("content", content.toString());
                fcmService.sendMulticast(data, listToken);
            } catch (FirebaseMessagingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void pushNotificationToOwnersWithoutTopic(MessageFCMModel messageFCMModel, List<UserFCMModel> userFCMS) {
        if (!userFCMS.isEmpty()) {
            try {
                List<String> listToken = new ArrayList<>();
                for (UserFCMModel userFCM : userFCMS) {
                    listToken.add(userFCM.getToken());
                }

                Map<String, String> data = new HashMap<>();
                data.put("type", "2");
                data.put("messageEn", messageFCMModel.getMessageEn());
                data.put("titleEn", messageFCMModel.getTitleEn());
                data.put("messageEs", messageFCMModel.getMessageEs());
                data.put("titleEs", messageFCMModel.getTitleEs());
                fcmService.sendMulticast(data, listToken);
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
                model.setUserId(userFCM.getUser().getUserId());
                model.setDeviceId(userFCM.getDeviceId());
                model.setToken(userFCM.getToken());
                list.add(model);
            }
        }
    }
}
