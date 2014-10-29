package com.mysoft.cjt188;

import java.util.Arrays;
import com.serotonin.messaging.IncomingRequestMessage;
import com.serotonin.messaging.OutgoingResponseMessage;
import com.serotonin.messaging.RequestHandler;

public class CjtRequestHandler implements RequestHandler {



	
	public CjtRequestHandler(){
	}
	@Override
	public OutgoingResponseMessage handleRequest(IncomingRequestMessage arg0)
			throws Exception {
		// TODO Auto-generated method stub
		CjtMessage dltmessage=((CjtIncomingRequestMessage)arg0).getCjtMessage();
		
		System.out.println(Arrays.toString(dltmessage.getData()));
		
		return null;
	}
	

}
