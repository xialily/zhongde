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
package com.serotonin.modbus4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.serotonin.messaging.MessageControl;
import com.serotonin.modbus4j.base.KeyedModbusLocator;
import com.serotonin.modbus4j.base.RangeAndOffset;
import com.serotonin.modbus4j.base.ReadFunctionGroup;
import com.serotonin.modbus4j.base.SlaveProfile;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.ExceptionCode;
import com.serotonin.modbus4j.code.FunctionCode;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.InvalidDataConversionException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ModbusRequest;
import com.serotonin.modbus4j.msg.ModbusResponse;
import com.serotonin.modbus4j.msg.ReadCoilsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadResponse;
import com.serotonin.modbus4j.msg.WriteCoilRequest;
import com.serotonin.modbus4j.msg.WriteMaskRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegistersRequest;
import com.serotonin.util.ArrayUtils;
import com.serotonin.util.ProgressiveTask;

abstract public class ModbusMaster extends Modbus {
    private int timeout = 500;
    private int retries = 2;
    private final Map<Integer, SlaveProfile> slaveProfiles = new HashMap<Integer, SlaveProfile>();
    protected boolean initialized;

    abstract public void init() throws ModbusInitException;

    public boolean isInitialized() {
        return initialized;
    }

    abstract public void destroy();

    abstract public ModbusResponse send(ModbusRequest request) throws ModbusTransportException;

    /**
     * Convenience method. See getValue(ModbusLocator)
     */
    public Object getValue(int slaveId, int registerId, int dataType) throws ModbusTransportException,
            ErrorResponseException {
        RangeAndOffset rao = new RangeAndOffset(registerId);
        return getValue(slaveId, rao.getRange(), rao.getOffset(), dataType);
    }

    /**
     * Convenience method. See getValue(ModbusLocator)
     */
    public Object getValue(int slaveId, int range, int offset, int dataType) throws ModbusTransportException,
            ErrorResponseException {
        return getValue(new ModbusLocator(slaveId, range, offset, dataType));
    }

    /**
     * Convenience method. See getValue(ModbusLocator)
     */
    public Boolean getValue(int slaveId, int registerId, byte bit) throws ModbusTransportException,
            ErrorResponseException {
        RangeAndOffset rao = new RangeAndOffset(registerId);
        return getValue(slaveId, rao.getRange(), rao.getOffset(), bit);
    }

    /**
     * Convenience method. See getValue(ModbusLocator)
     */
    public Boolean getValue(int slaveId, int range, int offset, byte bit) throws ModbusTransportException,
            ErrorResponseException {
        return (Boolean) getValue(new ModbusLocator(slaveId, range, offset, bit));
    }

    /**
     * Returns a value from the modbus network according to the given locator information. Various data types are
     * allowed to be requested including multi-word types. The determination of the correct request message to send is
     * handled automatically.
     * 
     * @param locator
     *            the information required to locate the value in the modbus network.
     * @return an object representing the value found. This will be one of Boolean, Short, Integer, Long, BigInteger,
     *         Float, or Double. See the DataType enumeration for details on which type to expect.
     * @throws ModbusTransportException
     *             if there was an IO error or other technical failure while sending the message
     * @throws ErrorResponseException
     *             if the response returned from the slave was an exception.
     */
    public Object getValue(ModbusLocator locator) throws ModbusTransportException, ErrorResponseException {
        BatchRead<String> batch = new BatchRead<String>();
        batch.addLocator("", locator);
        BatchResults<String> result = send(batch);
        return result.getValue("");
    }

    /**
     * Convenience method. See setValue(ModbusLocator, Object)
     */
    public void setValue(int slaveId, int registerId, int dataType, Object value) throws ModbusTransportException,
            ErrorResponseException {
        RangeAndOffset rao = new RangeAndOffset(registerId);
        setValue(slaveId, rao.getRange(), rao.getOffset(), dataType, value);
    }

    /**
     * Convenience method. See setValue(ModbusLocator, Object)
     */
    public void setValue(int slaveId, int range, int offset, int dataType, Object value)
            throws ModbusTransportException, ErrorResponseException {
        setValue(new ModbusLocator(slaveId, range, offset, dataType), value);
    }

    /**
     * Convenience method. See setValue(ModbusLocator, Object)
     */
    public void setValue(int slaveId, int registerId, byte bit, boolean value) throws ModbusTransportException,
            ErrorResponseException {
        RangeAndOffset rao = new RangeAndOffset(registerId);
        setValue(slaveId, rao.getRange(), rao.getOffset(), bit, value);
    }

    /**
     * Convenience method. See setValue(ModbusLocator, Object)
     */
    public void setValue(int slaveId, int range, int offset, byte bit, boolean value) throws ModbusTransportException,
            ErrorResponseException {
        setValue(new ModbusLocator(slaveId, range, offset, bit), value);
    }

    /**
     * Sets the given value in the modbus network according to the given locator information. Various data types are
     * allowed to be set including including multi-word types. The determination of the correct write message to send is
     * handled automatically.
     * 
     * @param locator
     *            the information required to locate the value in the modbus network.
     * @value an object representing the value to be set. This will be one of Boolean, Short, Integer, Long, BigInteger,
     *        Float, or Double. See the DataType enumeration for details on which type to expect.
     * @throws ModbusTransportException
     *             if there was an IO error or other technical failure while sending the message
     * @throws ErrorResponseException
     *             if the response returned from the slave was an exception.
     */
    public void setValue(ModbusLocator locator, Object value) throws ModbusTransportException, ErrorResponseException {
        int slaveId = locator.getSlaveAndRange().getSlaveId();
        int registerRange = locator.getSlaveAndRange().getRange();
        int writeOffset = locator.getOffset();

        // Determine the request type that we will use
        if (registerRange == RegisterRange.INPUT_STATUS || registerRange == RegisterRange.INPUT_REGISTER)
            throw new RuntimeException("Cannot write to input status or input register ranges");

        if (registerRange == RegisterRange.COIL_STATUS) {
            if (!(value instanceof Boolean))
                throw new InvalidDataConversionException("Only boolean values can be written to coils");
            setValue(new WriteCoilRequest(slaveId, writeOffset, ((Boolean) value).booleanValue()));
        }
        else {
            // Writing to holding registers.
            if (locator.getDataType() == DataType.BINARY) {
                if (!(value instanceof Boolean))
                    throw new InvalidDataConversionException("Only boolean values can be written to coils");
                setHoldingRegisterBit(slaveId, writeOffset, locator.getBit(), ((Boolean) value).booleanValue());
            }
            else {
                // Writing some kind of value to a holding register.
                if (!(value instanceof Number))
                    throw new InvalidDataConversionException("Numeric value is required");

                short[] data = locator.valueToShorts((Number) value);
                if (data.length == 1)
                    setValue(new WriteRegisterRequest(slaveId, writeOffset, data[0]));
                else
                    setValue(new WriteRegistersRequest(slaveId, writeOffset, data));
            }
        }

    }

    /**
     * Node scanning. Returns a list of slave nodes that respond to a read exception status request (perhaps with an
     * error, but respond nonetheless).
     * 
     * Note: a similar scan could be done for registers in nodes, but, for one thing, it would take some time to run,
     * and in any case the results would not be meaningful since there would be no semantic information accompanying the
     * results.
     */
    public List<Integer> scanForSlaveNodes() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= 240; i++) {
            if (testSlaveNode(i))
                result.add(i);
        }
        return result;
    }

    public ProgressiveTask scanForSlaveNodes(final NodeScanListener l) {
        l.progressUpdate(0);
        ProgressiveTask task = new ProgressiveTask(l) {
            private int node = 1;

            @Override
            protected void runImpl() {
                if (testSlaveNode(node))
                    l.nodeFound(node);

                declareProgress(((float) node) / 240);

                node++;
                if (node > 240)
                    completed = true;
            }
        };

        new Thread(task).start();

        return task;
    }

    public boolean testSlaveNode(int node) {
        try {
            send(new ReadHoldingRegistersRequest(node, 0, 1));
        }
        catch (ModbusTransportException e) {
            // If there was a transport exception, there's no node there.
            return false;
        }
        return true;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        if (retries < 0)
            this.retries = 0;
        else
            this.retries = retries;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 1)
            this.timeout = 1;
        else
            this.timeout = timeout;
    }

    /**
     * Useful for sending a number of polling commands at once, or at least in as optimal a batch as possible.
     */
    public <K> BatchResults<K> send(BatchRead<K> batch) throws ModbusTransportException, ErrorResponseException {
        if (!initialized)
            throw new ModbusTransportException("not initialized");

        BatchResults<K> results = new BatchResults<K>();
        List<ReadFunctionGroup<K>> functionGroups = batch.getReadFunctionGroups();

        // Execute each read function and process the results.
        for (ReadFunctionGroup<K> functionGroup : functionGroups)
            sendFunctionGroup(functionGroup, results, batch.isErrorsInResults(), batch.isExceptionsInResults());

        return results;
    }

    //
    // /
    // / Protected methods
    // /
    //
    protected MessageControl getMessageControl() {
        MessageControl conn = new MessageControl();
        conn.setRetries(getRetries());
        conn.setTimeout(getTimeout());
        conn.setExceptionHandler(getExceptionHandler());
        return conn;
    }

    protected void closeMessageControl(MessageControl conn) {
        if (conn != null)
            conn.close();
    }

    //
    // /
    // / Private stuff
    // /
    //
    /**
     * This method assumes that all locators have already been pre-sorted and grouped into valid requests, say, by the
     * createRequestGroups method.
     */
    private <K> void sendFunctionGroup(ReadFunctionGroup<K> functionGroup, BatchResults<K> results,
            boolean errorsInResults, boolean exceptionsInResults) throws ModbusTransportException,
            ErrorResponseException {
        int slaveId = functionGroup.getSlaveAndRange().getSlaveId();
        int startOffset = functionGroup.getStartOffset();
        int length = functionGroup.getLength();

        // Inspect the function group for data required to create the request.
        ModbusRequest request;
        if (functionGroup.getFunctionCode() == FunctionCode.READ_COILS)
            request = new ReadCoilsRequest(slaveId, startOffset, length);
        else if (functionGroup.getFunctionCode() == FunctionCode.READ_DISCRETE_INPUTS)
            request = new ReadDiscreteInputsRequest(slaveId, startOffset, length);
        else if (functionGroup.getFunctionCode() == FunctionCode.READ_HOLDING_REGISTERS)
            request = new ReadHoldingRegistersRequest(slaveId, functionGroup.getStartOffset(),
                    functionGroup.getLength());
        else if (functionGroup.getFunctionCode() == FunctionCode.READ_INPUT_REGISTERS)
            request = new ReadInputRegistersRequest(slaveId, functionGroup.getStartOffset(), functionGroup.getLength());
        else
            throw new RuntimeException("Unsupported function");

        ReadResponse response;
        try {
            response = (ReadResponse) send(request);
        }
        catch (ModbusTransportException e) {
            if (!exceptionsInResults)
                throw e;

            for (KeyedModbusLocator<K> locator : functionGroup.getLocators())
                results.addResult(locator.getKey(), e);

            return;
        }

        byte[] data = null;
        if (!errorsInResults && response.isException())
            throw new ErrorResponseException(request, response);
        else if (!response.isException())
            data = response.getData();

        for (KeyedModbusLocator<K> locator : functionGroup.getLocators()) {
            if (errorsInResults && response.isException())
                results.addResult(locator.getKey(), new ExceptionResult(response.getExceptionCode()));
            else {
                try {
                    results.addResult(locator.getKey(), locator.bytesToValue(data, startOffset));
                }
                catch (RuntimeException e) {
                    throw new RuntimeException("Result conversion exception. data=" + ArrayUtils.toHexString(data)
                            + ", startOffset=" + startOffset + ", dataType=" + locator.getDataType()
                            + ", locator.endOffset=" + locator.getEndOffset() + ", locator.length="
                            + locator.getLength() + ", locator.offset=" + locator.getOffset() + ", locator.bit="
                            + locator.getBit() + ", locator.key=" + locator.getKey() + ", locator.bit.class="
                            + locator.getKey().getClass() + ", functionGroup.functionCode="
                            + functionGroup.getFunctionCode() + ", functionGroup.startOffset="
                            + functionGroup.getStartOffset() + ", functionGroup.length=" + functionGroup.getLength(), e);
                }
            }
        }
    }

    private void setValue(ModbusRequest request) throws ModbusTransportException, ErrorResponseException {
        ModbusResponse response = send(request);
        if (response == null)
            // This should only happen if the request was a broadcast
            return;
        if (response.isException())
            throw new ErrorResponseException(request, response);
    }

    private void setHoldingRegisterBit(int slaveId, int writeOffset, byte bit, boolean value)
            throws ModbusTransportException, ErrorResponseException {
        // Writing a bit in a holding register field. There are two ways to do this. The easy way is to
        // use a write mask request, but it is not always supported. The hard way is to read the value, change
        // the appropriate bit, and then write it back again (so as not to overwrite the other bits in the
        // value). However, since the hard way is not atomic, it is not fail-safe either, but it should be
        // at least possible.
        SlaveProfile sp = getSlaveProfile(slaveId);
        if (sp.getWriteMaskRegister()) {
            // Give the write mask a try.
            WriteMaskRegisterRequest request = new WriteMaskRegisterRequest(slaveId, writeOffset);
            request.setBit(bit, value);
            ModbusResponse response = send(request);
            if (response == null)
                // This should only happen if the request was a broadcast
                return;
            if (!response.isException())
                // Hey, cool, it worked.
                return;

            if (response.getExceptionCode() == ExceptionCode.ILLEGAL_FUNCTION)
                // The function is probably not supported. Fail-over to the two step.
                sp.setWriteMaskRegister(false);
            else
                throw new ErrorResponseException(request, response);
        }

        // Do it the hard way. Get the register's current value.
        int regValue = (Integer) getValue(slaveId, RegisterRange.HOLDING_REGISTER, writeOffset,
                DataType.TWO_BYTE_INT_UNSIGNED);

        // Modify the value according to the given bit and value.
        if (value)
            regValue = regValue | 1 << bit;
        else
            regValue = regValue & ~(1 << bit);

        // Write the new register value.
        setValue(new WriteRegisterRequest(slaveId, writeOffset, regValue));
    }

    private SlaveProfile getSlaveProfile(int slaveId) {
        SlaveProfile sp = slaveProfiles.get(slaveId);
        if (sp == null) {
            sp = new SlaveProfile();
            slaveProfiles.put(slaveId, sp);
        }
        return sp;
    }
}
