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

import com.serotonin.messaging.IncomingMessage;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.util.queue.ByteQueue;

public class EncapMessageParser extends BaseMessageParser {
    public EncapMessageParser(boolean master) {
        super(master);
    }

    @Override
    protected IncomingMessage parseMessageImpl(ByteQueue queue) throws Exception {
        if (master){
        	byte []data=queue.peekAll();

        	
        	System.out.println("开始");
        	for(byte bb:data){
				
//				System.out.print(Integer.toHexString(bb&0xff)+" ");
//				System.out.println();
				String strHex = new String();
			    strHex = Integer.toHexString(bb).toUpperCase();
			    if(strHex.equalsIgnoreCase("0"));
			    if(strHex.length() > 3)
			     System.out.print(strHex.substring(6) + "  ");
			    else
			     if(strHex.length() < 2)
			      System.out.print("0" + strHex + "  ");
			     else
			      System.out.print(strHex + "  ");
			}
			System.out.println("结束");
//					System.out.println();
       	
        	/*
        	queue.pop(data);
        	
        	System.out.println("heart:"+Arrays.toString(data));
        	ByteQueue newqueue=null;
        	newqueue=new ByteQueue();
        	newqueue.push(data);
        	*/
            return EncapMessageResponse.createEncapMessageResponse(queue);
      		
        }
        return EncapMessageRequest.createEncapMessageRequest(queue);
    }
}
