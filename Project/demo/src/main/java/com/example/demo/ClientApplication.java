package com.example.demo;

import com.caucho.hessian.io.HessianInput;
import com.example.demo.hessian.SensorID;
import com.example.demo.hessian.SensorReadDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jfree.ui.RefineryUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import com.example.demo.hessian.Message;

import java.io.ByteArrayInputStream;
import java.util.*;


@SpringBootApplication
public class ClientApplication {

	@Autowired
	private Environment env;

	private static UUID sensorId;


	@Bean
	public HessianProxyFactoryBean hessianInvoker() {
		HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
		invoker.setServiceUrl(env.getProperty("server_url"));
		sensorId = UUID.fromString(env.getProperty("sensorId"));
		invoker.setServiceInterface(Message.class);
		return invoker;
	}


	public static void main(String[] args) {

		ConfigurableApplicationContext context =  SpringApplication.run(ClientApplication.class, args);
		System.out.println("========Client Side===============");
		Message helloWorld =     context.getBean(Message.class);

		Object result = null;
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(helloWorld.transferWithHessian(sensorId));
			HessianInput input = new HessianInput(is);
			result = input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<HashMap<String,String>> s = (ArrayList<HashMap<String, String>>) result;

		Gson gson = new Gson();
		List<SensorReadDto> list = new ArrayList<>();
		for(HashMap m : s) {
			JsonElement jsonElement = gson.toJsonTree(m);
			SensorReadDto pojo = gson.fromJson(jsonElement, SensorReadDto.class);
			list.add(pojo);
		}

		System.out.println("Deserializat: " + list.size() + " : " + list.get(0));


		//Map<Integer,SensorReadDto> map = list.get(2);
		//SensorReadDto p = list.get(0);
//		List<SensorReadDto> n = new ArrayList<>(list);
//		System.out.println("Tot: "  + ", doar ceva: " + n.get(0).getClass());
//		System.out.println("List: " + list.get(2).getTimestamp());


		//Chart
		System.setProperty("java.awt.headless", "false");

		Chart chart = new Chart("Consumption",
				"Last 7 days", list);
		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );

		ChartBaseline baseline = new ChartBaseline("Avg comsumption", "avg/h", list);
		baseline.pack();
		RefineryUtilities.centerFrameOnScreen(baseline);
		baseline.setVisible(true);

	}

}
