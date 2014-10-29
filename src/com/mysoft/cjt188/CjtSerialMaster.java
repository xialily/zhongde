package com.mysoft.cjt188;

import gnu.io.SerialPort;

import java.io.IOException;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.io.serial.SerialUtils;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class CjtSerialMaster extends CjtMaster  {
    // Runtime fields.
    private final SerialParameters serialParameters;

    // Runtime fields.
    protected SerialPort serialPort;

	public CjtSerialMaster(SerialParameters params) {
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

       CjtIpSerialMessageResponse lqresponse=new CjtIpSerialMessageResponse(null);
//       LqRequestHandler lqserialrequesthandler=new LqRequestHandler(this,lqresponse);
        CjtIpSerialMessageParser cjtMessageParser = new CjtIpSerialMessageParser(true);
        conn = getMessageControl();
        try {
            conn.start(transport, cjtMessageParser, null);
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
	public CjtMessage send(CjtMessage request)
			throws ModbusTransportException {
		// TODO Auto-generated method stub
        CjtIpSerialMessageRequest cjtRequest = new CjtIpSerialMessageRequest(request);

        if(conn.DEBUG){
		byte b[]=null;
		b=cjtRequest.getMessageData();
		for(byte bb:b)
		System.out.print(Integer.toHexString(bb&0xff)+" ");
				System.out.println();
        }
       
        // Send the request to get the response.
        CjtIpSerialMessageResponse cjtResponse=null;
        try {
            cjtResponse = (CjtIpSerialMessageResponse) conn.send(cjtRequest);
            if(conn.DEBUG){
    		byte b[]=null;
    		b=cjtResponse.getMessageData();
    		for(byte bb:b)
    		System.out.print(Integer.toHexString(bb&0xff)+" ");
    				System.out.println();
            }
            if (cjtResponse == null)
                return null;
            return cjtResponse.getCjtMessage();
        }
        catch (Exception e) {
            throw new ModbusTransportException(e);
        }
}

}
