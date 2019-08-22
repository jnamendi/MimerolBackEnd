package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.util.Constant;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    public static void sendSmsToClient(String codeOrder,String phoneNumber,String time ,String getLanguageCode){
        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(Constant.AmazonKey.ACCESS_KEY, Constant.AmazonKey.SECRET_KEY));
        String message =null;
        if (getLanguageCode.equals("en")){
            message = String.format(Constant.ContentSmsClient.EN,codeOrder,time);
        }else if (getLanguageCode.equals("es")){
            message = String.format(Constant.ContentSmsClient.ES,codeOrder,time);
        }
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber));
        System.out.println(result);
    }

    public static void sendSmsToOWner(String codeOrder,String phoneNumber,String getLanguageCode){
        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(Constant.AmazonKey.ACCESS_KEY,Constant.AmazonKey.SECRET_KEY));
        String message =null;
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<>();
        //<set SMS attributes>
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("MIMEROL") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to promotional.
                .withDataType("String"));

        if (getLanguageCode.equals("en")){
            message = String.format(Constant.ContentSmsRestaurant.EN,codeOrder);
        }else if (getLanguageCode.equals("es")){
            message = String.format(Constant.ContentSmsRestaurant.ES,codeOrder);
        }

        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);

    }

    private static void sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
    }
}
