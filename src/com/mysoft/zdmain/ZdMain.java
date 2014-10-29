package com.mysoft.zdmain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysoft.entities.ZdParam;
import com.mysoft.services.IB04DeviceService;
import com.mysoft.services.IControllerService;
import com.mysoft.services.ID01ElectricityDataService;
import com.mysoft.services.ID02WaterDataService;
import com.mysoft.services.ID03HeatDataService;
import com.mysoft.services.IZdParamService;
/**
 * 主类包含main函数
 * 
 * @author yangfan ej
 *
 */
public class ZdMain {
	public static final ClassPathXmlApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		IZdParamService zdparamservice=getZdParamService();
		ZdParam param=zdparamservice.findByParamName("interval");
		int interval=Integer.parseInt(param.getParamValue());
		ScheduledExecutorService spool;
		spool=Executors.newSingleThreadScheduledExecutor();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		System.out.println("ZdMain time = " + str);
		spool.scheduleWithFixedDelay(new ZdService(), 1, interval, TimeUnit.SECONDS);

//		String exp = " ipAddress='192.168.1.27' and status='0' ";
//		List<B04Device> lw2 = ZdMain.getB04DeviceService().list(
//				exp + " and deviceType='6'");
		
//		IControllerService controllers=ZdMain.getControllerService();
//		List<Controller> lcontroller=controllers.list(null);
//		for(Controller cont:lcontroller){
//			System.out.println(cont.getIpaddr());
//		}
		
//		IB04DeviceService b04devices=ZdMain.getB04DeviceService();
//		List<B04Device> le = b04devices.list(null);
//		for(B04Device d:lw2){
//			System.out.println(d.getEquipmentType());
//			System.out.println(d.getEquipmentId());
//		}
		
/*		ID01ElectricityDataService des=ZdMain.getD01ElectricityDataService();
		List<D01ElectricityData> lee=des.list(null);
		for(D01ElectricityData e:lee){
			System.out.println(e.getVvalue());
		}
*/	


/*		ID02WaterDataService dws=ZdMain.getD02WaterDataService();
		
		List<D02WaterData> lw=dws.list(null);
		D02WaterData data=new D02WaterData();
		double result=22;
	//	result=reader.readTotalFlow(d.getEquipmentId());
		data.setTotalstream(result);
		data.setB04id(11);
	//	result=reader.readInstantFlow(d.getEquipmentId());
		data.setCurrentstream(result);
		Date date=new Date();
		SimpleDateFormat month=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat hour=new SimpleDateFormat("HHmmss");
		String mon=month.format(date);
		String hou=hour.format(date);
		data.setMonthno(mon);
		data.setHourtime(hou);
	//	System.out.println(mon+" "+hou);
		//data.setId(1);
		dws.add(data);
*/	
		
/*		ID03HeatDataService dws=ZdMain.getD03HeatDataService();
		List<D03HeatData> lw=dws.list(null);
		for(D03HeatData w:lw){
			System.out.print(w.getCurrentvalue());
		}
*/		
	
	}
	/**
	 * @return the ctx
	 */
	public static ClassPathXmlApplicationContext getCtx() {
		return ctx;
	}
	public static ID01ElectricityDataService getD01ElectricityDataService(){
		return (ID01ElectricityDataService)ctx.getBean("d01ElectricityDataService");
	}
	public static ID02WaterDataService getD02WaterDataService(){
		return (ID02WaterDataService)ctx.getBean("d02WaterDataService");
	}
	public static ID03HeatDataService getD03HeatDataService(){
		return (ID03HeatDataService)ctx.getBean("d03HeatDataService");
	}
	public static IB04DeviceService getB04DeviceService(){
		return (IB04DeviceService)ctx.getBean("b04DeviceService");
	}
	public static IZdParamService getZdParamService(){
		return (IZdParamService)ctx.getBean("zdParamService");
	}
	public static IControllerService getControllerService(){
		return (IControllerService)ctx.getBean("controllerService");
	}

}
