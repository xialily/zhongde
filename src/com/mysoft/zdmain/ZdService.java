package com.mysoft.zdmain;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.mysoft.entities.Controller;
import com.mysoft.entities.ZdParam;
import com.mysoft.services.IControllerService;
import com.mysoft.services.IZdParamService;

public class ZdService implements Runnable {
    private final ExecutorService pool;

    public ZdService(){
    	IZdParamService paramservice=ZdMain.getZdParamService();
    	ZdParam param=paramservice.findByParamName("threadpool_size");
    	int poolSize=Integer.parseInt(param.getParamValue());
    	pool=Executors.newFixedThreadPool(poolSize);
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		IControllerService controllers=ZdMain.getControllerService();
		List<Controller> lcontroller=controllers.list(null);
		for(Controller cont:lcontroller){	//18个的话
			try {
				
				pool.execute(new ZdHandler(cont));
				Thread.sleep(140000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
