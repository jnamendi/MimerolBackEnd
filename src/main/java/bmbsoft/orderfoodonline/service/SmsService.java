package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.util.Constant;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    @Autowired
    public Environment environment;

    public void sendSms(String codeOrder, String phoneNumber, String getLanguageCode, String time, Boolean isCustomer){
        String AccessKey = environment.getProperty("sms.public.key");
        String SecretKey = environment.getProperty("sms.secret.key");
        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(AccessKey,SecretKey));
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
            message = isCustomer ? String.format(Constant.ContentSmsClient.EN,codeOrder,time) : String.format(Constant.ContentSmsRestaurant.EN,codeOrder);

        }else if (getLanguageCode.equals("es")){
            message = isCustomer ? String.format(Constant.ContentSmsClient.ES,codeOrder,time) : String.format(Constant.ContentSmsRestaurant.ES,codeOrder);
        }

        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);

    }

    private void sendSMSMessage(AmazonSNSClient snsClient, String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
    }
}
