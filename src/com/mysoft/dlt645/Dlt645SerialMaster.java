package com.mysoft.dlt645;

import gnu.io.SerialPort;

import java.io.IOException;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.io.serial.SerialUtils;
import com.serotonin.messaging.StreamTransport;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;

public class Dlt645SerialMaster extends Dlt645Master  {
    // Runtime fields.
    private final SerialParameters serialParameters;

    // Runtime fields.
    protected SerialPort serialPort;

	public Dlt645SerialMaster(SerialParameters params) {
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

       Dlt645IpSerialMessageResponse lqresponse=new Dlt645IpSerialMessageResponse(null);
//       LqRequestHandler lqserialrequesthandler=new LqRequestHandler(this,lqresponse);
        Dlt645IpSerialMessageParser dltMessageParser = new Dlt645IpSerialMessageParser(true);
        conn = getMessageControl();
        try {
            conn.start(transport, dltMessageParser, null);
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
	public Dlt645Message send(Dlt645Message request)
			throws ModbusTransportException {
		// TODO Auto-generated method stub
        Dlt645IpSerialMessageRequest dltRequest = new Dlt645IpSerialMessageRequest(request);

        if(conn.DEBUG){
		byte b[]=null;
		b=dltRequest.getMessageData();
		for(byte bb:b)
		System.out.print(Integer.toHexString(bb&0xff)+" ");
				System.out.println();
        }
       
        // Send the request to get the response.
        Dlt645IpSerialMessageResponse dltResponse=null;
        try {
            dltResponse = (Dlt645IpSerialMessageResponse) conn.send(dltRequest);
            if(conn.DEBUG){
    		byte b[]=null;
    		b=dltResponse.getMessageData();
    		for(byte bb:b)
    		System.out.print(Integer.toHexString(bb&0xff)+" ");
    				System.out.println();
            }
            if (dltResponse == null)
                return null;
            return dltResponse.getDltMessage();
        }
        catch (Exception e) {
            throw new ModbusTransportException(e);
        }
}

}
