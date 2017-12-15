package com.twilio;

import com.twilio.twiml.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CallBackServlet extends HttpServlet {

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws IOException {
        //instantiating the voice response builder to add say clauses in the for loop
        VoiceResponse.Builder twiml = new VoiceResponse.Builder();
        String digits = request.getParameter("Digits");

        //checking the response for the digit then loops to say the appropriate term. If digits is null, I use appendGather to gather input from the phone call.
        if(digits != null) {
            int val = Integer.parseInt(digits);

            for (int i = 1; i <= val; i++) {
                if (i % 3 == 0 && i % 5 == 0) {
                    twiml.say(new Say.Builder("fizzbuzz").build());
                    continue;
                }
                if (i % 5 == 0) {
                    twiml.say(new Say.Builder("buzz").build());
                    continue;

                }
                if (i % 3 == 0) {
                    twiml.say(new Say.Builder("fizz").build());
                    continue;
                }
                twiml.say(new Say.Builder(String.valueOf(i)).build());
            }
        }
        else {
            appendGather(twiml);
        }
        response.setContentType("application/xml");
        try {
            response.getWriter().print(twiml.build().toXml());
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
    }

    //building gather object to get user input from the call then redirecting it back to the /callback to run the fizzbuzz loop.
    private static void appendGather(VoiceResponse.Builder builder) {
        builder.gather(new Gather.Builder()
                .finishOnKey("#")
                .say(new Say.Builder("Please enter a number between 0 and 100. Followed by the pound sign.").build())
                .build()
        )
                .redirect(new Redirect.Builder().url("callback").build());
    }

}
