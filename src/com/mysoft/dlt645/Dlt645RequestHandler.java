package com.mysoft.dlt645;

import java.util.Arrays;
import com.serotonin.messaging.IncomingRequestMessage;
import com.serotonin.messaging.OutgoingResponseMessage;
import com.serotonin.messaging.RequestHandler;

public class Dlt645RequestHandler implements RequestHandler {



	
	public Dlt645RequestHandler(){
	}
	@Override
	public OutgoingResponseMessage handleRequest(IncomingRequestMessage arg0)
			throws Exception {
		// TODO Auto-generated method stub
		Dlt645Message dltmessage=((Dlt645IncomingRequestMessage)arg0).getDltMessage();
		
		System.out.println(Arrays.toString(dltmessage.getData()));
		
		return null;
	}
	

}
