/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.modbus4j.ip.encap;

import com.serotonin.modbus4j.base.ModbusUtils;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpMessageResponse;
import com.serotonin.modbus4j.msg.ModbusMessage;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.util.queue.ByteQueue;

public class EncapMessageResponse extends EncapMessage implements IpMessageResponse {
    static EncapMessageResponse createEncapMessageResponse(ByteQueue queue) throws ModbusTransportException {
        // Create the modbus response.
//    	int id = ModbusUtils.popUnsignedByte(queue);
//        byte function = queue.pop();

    	byte []data = queue.peekAll();
//    	while(true){
//    	if(data.length >= 2){
//	    	for(int i = 0; i < data.length; i++){
//		    	if(data[1] == (byte)3){
//		    		System.out.println("data[1] 3= " + data[1]);
//		    	}else{
//		    		System.out.println("data[1]= " + data[1]);
//		    		queue.pop();
//		    		queue.pop();
//		    		data = queue.peekAll();
//		    	}
//	    	}
	    	
//	    	System.out.println("开始1");
//	    	for(byte bb:data){
//	    	String strHex = new String();
//		    strHex = Integer.toHexString(bb).toUpperCase();
////		    if(strHex.equalsIgnoreCase("0"));
//		    if(strHex.length() > 3)
//		     System.out.print(strHex.substring(6) + "  ");
//		    else
//		     if(strHex.length() < 2)
//		      System.out.print("0" + strHex + "  ");
//		     else
//		      System.out.print(strHex + "  ");
//	    	}
//	    	System.out.println("结束1");
	        ModbusResponse response = ModbusResponse.createModbusResponse(queue);
	        EncapMessageResponse encapResponse = new EncapMessageResponse(response);
	        // Check the CRC
	        ModbusUtils.checkCRC(encapResponse.modbusMessage, queue);
	        return encapResponse;
    	
    	
    	
    	
    	

        
        
        
    }

    public EncapMessageResponse(ModbusResponse modbusResponse) {
        super(modbusResponse);
    }

    @Override
	public ModbusResponse getModbusResponse() {
        return (ModbusResponse) modbusMessage;
    }
}
