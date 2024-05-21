package Inpres.masi.backend_Authentication.Twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsController {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.service.sid}")
    private String serviceSid;

    @Value("${twilio.phoneNumber}")
    private String phoneNumberSource;

    public void sendSms(String phoneNumber, String message) {
        Twilio.init(accountSid, authToken);

        Message.creator(new PhoneNumber(phoneNumber),
                new PhoneNumber(phoneNumberSource), message).create();
    }
}
