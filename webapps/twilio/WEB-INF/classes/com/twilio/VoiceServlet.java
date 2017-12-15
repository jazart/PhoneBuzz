package com.twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VoiceServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //REPLACE THESE VALUES WITH YOUR ACCOUNT SID AND AUTH TOKEN
        final String ACCOUNT_SID = "";
        final String AUTH_TOKEN = "";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        //getting the user's number from the index.html response, building the phone call and posting it to the callback servlet.
        //NOTE: REPLACE THE SECOND PHONE NUMBER WITH YOUR TWILIO NUMBER.
        //NOTE: REPLACE THE URI WITH "https://<Your ngrok subdomain>.ngrok.io/twilio/callback
        try {
            Call call = Call.creator(new PhoneNumber(request.getParameter("phoneNum")),
                    new PhoneNumber(""),
                    new URI("https://<your-ngrok-domain>.ngrok.io/twilio/callback")).create();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


}
