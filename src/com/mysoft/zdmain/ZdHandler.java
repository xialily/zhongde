package com.mysoft.zdmain;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.mysoft.devicereader.*;
import com.mysoft.entities.*;
import com.mysoft.services.*;

public class ZdHandler implements Runnable {
	private Controller controller;

	public ZdHandler(Controller controller) {
		this.controller = controller;
	}

	public ZdHandler() {

	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		ZdParam param = ZdMain.getZdParamService().findByParamName("connect_retries");
		int retries = Integer.parseInt(param.getParamValue());
		Socket socket = null;
		String ipaddr = controller.getIpaddr();
		int ipport = controller.getIpport();
		// String ipaddr = "192.168.1.26";
		// int ipport = 4001;
		long m = System.currentTimeMillis();

		while (retries > 0) {
			try {
				String exp = " ipAddress='" + ipaddr + "' and status='0' ";
				List<B04Device> le = ZdMain.getB04DeviceService().list(
						exp + " and deviceType='1'");
				List<B04Device> lh = ZdMain.getB04DeviceService().list(
						exp + " and deviceType='3'");
				List<B04Device> lw2 = ZdMain.getB04DeviceService().list(
						exp + " and deviceType='2'");
				List<B04Device> lw = ZdMain.getB04DeviceService().list(
						exp + " and deviceType='6'");

				if (le == null && le.size() <= 0 && lw == null
						&& lw.size() <= 0 && lw2 == null && lw2.size() <= 0
						&& lh == null && lh.size() <= 0)
					break;
				boolean status = InetAddress.getByName(ipaddr).isReachable(3000);
				if (status == true) {
					System.out
							.println("==========================================远程集中器：" + ipaddr + "可以连通");
				} else {
					System.out
							.println("==========================================远程集中器：" + ipaddr + "不可到达");
					break;
				}
				long l = System.currentTimeMillis();
				System.out.println(ipaddr + ":" + ipport + "开始建立连接");
				socket = new Socket();
				socket.connect(
						new InetSocketAddress(InetAddress.getByName(ipaddr), ipport), 60000);

				if (socket != null) {
					System.out.println(ipaddr + ":" + ipport + "连接建立成功" + (System.currentTimeMillis() - l));

					// 电表

					if (le != null && le.size() > 0) {
						ID01ElectricityDataService dws = ZdMain.getD01ElectricityDataService();
						Dlt645Reader reader = new Dlt645Reader(ipaddr, ipport, socket);
						for (B04Device d : le) {

							try {
								double voltage = 0, current = 0, activePower = 0, reactivePower = 0, electricEnergy = 0;

								String eid = String.valueOf(d.getTen());
								voltage = reader.readAVoltage(eid);// A相电压
								Thread.sleep(1000);
								if(voltage==999){
									continue;
								}

								current = reader.readACurrent(eid);// A相电流
								Thread.sleep(1000);
								if(current==999){
									continue;
								}
								
								electricEnergy = reader.readEnergy(eid);// 有功总电能
								Thread.sleep(1000);
								if(electricEnergy==999){
									continue;
								}
								
								activePower = reader.readActivePower(eid);// 瞬时总有功率功率
								Thread.sleep(1000);
								if(activePower==999){
									continue;
								}
								
								reactivePower = reader.readReactivePower(eid);// 瞬时总无功功率
								Thread.sleep(1000);
								if(reactivePower==999){
									continue;
								}
								
								Date date = new Date();
								SimpleDateFormat month = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
								String mon = month.format(date);
								String hou = hour.format(date);

								D01ElectricityData data = new D01ElectricityData();

								data.setB04id(d.getId());
								data.setMonthno(mon);
								data.setHourtime(hou);
								data.setVvalue(voltage);
								data.setAvalue(current);
								data.setYpwvalue(activePower);
								data.setNpwvalue(reactivePower);
								data.setKwhvalue(electricEnergy * d.getHuganbi());

								dws.add(data);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						reader.destroy();
					}

					// 消防水表：188协议
					if (lw != null && lw.size() > 0) {
						ID02WaterDataService dws = ZdMain
								.getD02WaterDataService();
						CjtReader reader = new CjtReader(ipaddr, ipport, socket);
						for (B04Device d : lw) {
							try {

								double result = 0;
								String equipmentId = d.getEquipmentId();

								result = reader.readFlow(equipmentId);
								Thread.sleep(1000);

								if (result == 999){
									continue;
								}

								Date date = new Date();
								SimpleDateFormat month = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
								String mon = month.format(date);
								String hou = hour.format(date);

								D02WaterData data = new D02WaterData();
								data.setB04id(d.getId());
								data.setTotalstream(result);
								data.setCurrentstream(0);
								data.setMonthno(mon);
								data.setHourtime(hou);
								dws.add(data);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						reader.destroy();
					}

					// 热表
					if (lh != null && lh.size() > 0) {
						ID03HeatDataService dws = ZdMain.getD03HeatDataService();
						ModbusReader reader = new ModbusReader(ipaddr, ipport,socket);

						for (B04Device d : lh) {
							try {

								double totalHeat = 0, instantHeat = 0;

								totalHeat = reader.readTotalHeat(d.getEquipmentId());
								Thread.sleep(1000);
								if (totalHeat==999){
									continue;
								}
								
								instantHeat = reader.readInstantHeat(d.getEquipmentId());
								Thread.sleep(1000);
								if (instantHeat==999){
									continue;
								}

								Date date = new Date();
								SimpleDateFormat month = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
								String mon = month.format(date);
								String hou = hour.format(date);

								D03HeatData data = new D03HeatData();
								data.setB04id(d.getId());
								data.setMonthno(mon);
								data.setHourtime(hou);
								data.setCurrentvalue(instantHeat);
								data.setTotalstream(totalHeat);

								dws.add(data);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						reader.destroy();
					}

					// 超声波水表
					if (lw2 != null && lw2.size() > 0) {
						
						ID02WaterDataService dws = ZdMain.getD02WaterDataService();
						ModbusReader reader = new ModbusReader(ipaddr, ipport,socket);
						for (B04Device d : lw2) {
							try {

								double totalFlow = 0, instantFlow = 0;

								instantFlow = reader.readInstantFlow(d.getEquipmentId());
								Thread.sleep(1000);
								if (instantFlow==999){
									continue;
								}
								
								totalFlow = reader.readTotalFlow(d.getEquipmentId());
								Thread.sleep(1000);
								if (totalFlow==999){
									continue;
								}
								
								Date date = new Date();
								SimpleDateFormat month = new SimpleDateFormat("yyyyMMdd");
								SimpleDateFormat hour = new SimpleDateFormat("HHmmss");
								String mon = month.format(date);
								String hou = hour.format(date);

								D02WaterData data = new D02WaterData();
								data.setB04id(d.getId());
								data.setMonthno(mon);
								data.setHourtime(hou);
								data.setCurrentstream(instantFlow);
								data.setTotalstream(totalFlow);

								dws.add(data);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						reader.destroy();
					}
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if (e instanceof ConnectException) {
					System.out.println("timeout");
				} else {
					System.out.println("==========================" + ipaddr + ":" + ipport + "未成功建立连接。");
					// e.printStackTrace();
				}

			} finally {

				if (socket != null) {
					try {
						socket.close();
						socket = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

			}
			--retries;
		}
		System.out.println(ipaddr + ":" + ipport + "运行时间为：" + (System.currentTimeMillis() - m));
	}

}
