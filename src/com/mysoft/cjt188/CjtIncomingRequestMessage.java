package com.mysoft.cjt188;

import com.serotonin.messaging.IncomingRequestMessage;

public interface CjtIncomingRequestMessage extends IncomingRequestMessage,Cloneable{
	public CjtMessage getCjtMessage();
	public void setCjtMessage(CjtMessage cjtMessage);

}
