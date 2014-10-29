/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serotonin.modbus4j.ip.tcp;

/**
 *
 * @author root
 */
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.serotonin.messaging.MessageControl;
import com.serotonin.messaging.TestableTransport;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.base.BaseMessageParser;
import com.serotonin.modbus4j.base.BaseRequestHandler;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.encap.EncapMessageParser;
import com.serotonin.modbus4j.ip.encap.EncapRequestHandler;
import com.serotonin.modbus4j.ip.xa.XaMessageParser;
import com.serotonin.modbus4j.ip.xa.XaRequestHandler;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpSlave2 extends ModbusSlaveSet {
    // Configuration fields

    private final IpParameters ipParameters;
    final boolean encapsulated;
    // Runtime fields.
    private Socket socket;
    private int timeout;
    private int retries;
    private ScheduledExecutorService spool;
    
    public TcpSlave2(IpParameters ipParameters, boolean encapsulated) {
        this.ipParameters = ipParameters;
        this.encapsulated = encapsulated;
        spool=Executors.newSingleThreadScheduledExecutor();
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        if (retries < 0) {
            this.retries = 0;
        } else {
            this.retries = retries;
        }
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 1) {
            this.timeout = 1;
        } else {
            this.timeout = timeout;
        }
    }

    @Override
    public void start() throws ModbusInitException {

        socket = new Socket();

        int retries = getRetries();
        while (true) {

            try {
                socket.connect(new InetSocketAddress(ipParameters.getHost(), ipParameters.getPort()), getTimeout());
                if (socket != null) {
                    TcpSlave2.TcpConnectionHandler handler = new TcpSlave2.TcpConnectionHandler(socket);
                    (new Thread(handler)).start();
                    HeartBeat hb=new HeartBeat(socket);
                    hb.sendLogin();
                    spool.execute(hb);
                }
                break;
            } catch (IOException e) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(TcpSlave2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (retries <= 0) {
                    new ModbusInitException(e);
                }
                // System.out.println("Modbus4J: Open connection failed, trying again.");
                retries--;
           }
       }
    }

    @Override
    public void stop() {
        // Close the socket first to prevent new messages.
        try {
            socket.close();
        } catch (IOException e) {
            getExceptionHandler().receivedException(e);
        }

        // Now close the executor service.

    }

    class TcpConnectionHandler implements Runnable {

        private final Socket socket;
        private TestableTransport transport;
        private MessageControl conn;

        TcpConnectionHandler(Socket socket) throws ModbusInitException {
            this.socket = socket;
            try {
                transport = new TestableTransport(socket.getInputStream(), socket.getOutputStream());
            } catch (IOException e) {
                throw new ModbusInitException(e);
            }
        }

        @Override
		public void run() {
            BaseMessageParser messageParser;
            BaseRequestHandler requestHandler;

            if (encapsulated) {
                messageParser = new EncapMessageParser(false);
                requestHandler = new EncapRequestHandler(TcpSlave2.this);
            } else {
                messageParser = new XaMessageParser(false);
                requestHandler = new XaRequestHandler(TcpSlave2.this);
            }

            conn = new MessageControl();
            conn.setExceptionHandler(getExceptionHandler());

            try {
                conn.start(transport, messageParser, requestHandler);
                transport.start("slave trans");
            } catch (IOException e) {
                getExceptionHandler().receivedException(new ModbusInitException(e));
            }

            // Monitor the socket to detect when it gets closed.
            while (true) {
                try {
                    transport.testInputStream();
                } catch (IOException e) {
                    break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // no op
                }
            }

            conn.close();
            try {
                socket.close();
            } catch (IOException e) {
                getExceptionHandler().receivedException(new ModbusInitException(e));
            }
        }
    }
}
