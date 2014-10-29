package com.mysoft.dlt645;

import com.serotonin.messaging.IncomingRequestMessage;

public interface Dlt645IncomingRequestMessage extends IncomingRequestMessage,Cloneable{
	public Dlt645Message getDltMessage();
	public void setDltMessage(Dlt645Message dltMessage);

}
