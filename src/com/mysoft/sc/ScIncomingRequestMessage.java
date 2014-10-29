package com.mysoft.sc;

import com.serotonin.messaging.IncomingRequestMessage;

public interface ScIncomingRequestMessage extends IncomingRequestMessage,Cloneable{
	public ScMessage getScMessage();
	public void setScMessage(ScMessage scMessage);

}
