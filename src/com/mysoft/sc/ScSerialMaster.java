package com.mysoft.sc;

import gnu.io.SerialPort;

import java.io.IOException;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.io.serial.SerialUtils;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class ScSerialMaster extends ScMaster  {
    // Runtime fields.
    private final SerialParameters serialParameters;

    // Runtime fields.
    protected SerialPort serialPort;

	public ScSerialMaster(SerialParameters params) {
		super();
		serialParameters = params;
		// TODO Auto-generated constructor stub
	}
   
    @Override
	public void init() throws ModbusInitException {
       try {
           serialPort = SerialUtils.openSerialPort(serialParameters);
           transport = new StreamTransport(serialPort.getInputStream(), serialPort.getOutputStream());
       }
       catch (Exception e) {
           throw new ModbusInitException(e);
       }

       ScIpSerialMessageResponse lqresponse=new ScIpSerialMessageResponse(null);
//       LqRequestHandler lqserialrequesthandler=new LqRequestHandler(this,lqresponse);
        ScIpSerialMessageParser scMessageParser = new ScIpSerialMessageParser(true);
        conn = getMessageControl();
        try {
            conn.start(transport, scMessageParser, null);
          //  transport.setReadDelay(200);
            transport.start("Modbus lq master");
        }
        catch (IOException e) {
            throw new ModbusInitException(e);
        }
        conn.DEBUG=true;
        initialized = true;
    }


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
        closeMessageControl(conn);
        SerialUtils.close(serialPort);
		
	}


	@Override
	public ScMessage send(ScMessage request)
			throws ModbusTransportException {
		// TODO Auto-generated method stub
        ScIpSerialMessageRequest scRequest = new ScIpSerialMessageRequest(request);

        if(conn.DEBUG){
		byte b[]=null;
		b=scRequest.getMessageData();
		for(byte bb:b)
		System.out.print(Integer.toHexString(bb&0xff)+" ");
				System.out.println();
        }
       
        // Send the request to get the response.
        ScIpSerialMessageResponse scResponse=null;
        try {
            scResponse = (ScIpSerialMessageResponse) conn.send(scRequest);
            if(conn.DEBUG){
    		byte b[]=null;
    		b=scResponse.getMessageData();
    		for(byte bb:b)
    		System.out.print(Integer.toHexString(bb&0xff)+" ");
    				System.out.println();
            }
            if (scResponse == null)
                return null;
            return scResponse.getScMessage();
        }
        catch (Exception e) {
            throw new ModbusTransportException(e);
        }
}

}
