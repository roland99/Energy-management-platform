package ro.tuc.ds2020.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import ro.tuc.ds2020.hessian.Message;
import ro.tuc.ds2020.hessian.MessageImp;
import ro.tuc.ds2020.services.QueueListener;
import ro.tuc.ds2020.services.SensorService;

@Configuration
public class HessianConfiguration {

    @Autowired
    SensorService sensorService;

    @Bean(name="/hessianMessage")
    RemoteExporter transferWithHessianService(){
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(sensorService);
        exporter.setServiceInterface(Message.class);
        return exporter;
    }
}
