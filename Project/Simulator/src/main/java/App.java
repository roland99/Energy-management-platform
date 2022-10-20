import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class App {
    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://pnncinul:H5h677q4QriZJ6AA7LVrIUEFy9JwmCYK@cow.rmq2.cloudamqp.com/pnncinul");
        factory.setConnectionTimeout(30000);
        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();
        channel.queueDeclare("SD", false, false, false, null);

        //channel.basicPublish("","SD",null, message.getBytes());

        // Read config file
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream("src/main/config.properties");
        prop.load(file);

        long back7Days = System.currentTimeMillis()/1000 - 604800;
        long currentDate = System.currentTimeMillis()/1000;

        //Read csv file with value and sent to queue
        File csvFile = new File("src/main/resources/sensor.csv");
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // do something with the data[0]
                Timestamp timestamp = new Timestamp(back7Days * 1000);
                back7Days = back7Days + 3600;
                JSONObject json = new JSONObject();
                json.put("timestamp", timestamp.getTime());
                json.put("sensorId", prop.getProperty("sensorId"));
                json.put("measurement", data[0]);
                System.out.println(json);
                channel.basicPublish("","SD",null, json.toJSONString().getBytes());
                try {
                    //TimeUnit.SECONDS.sleep(1);
                    TimeUnit.MILLISECONDS.sleep(100);
                    //TimeUnit.MINUTES.sleep(1); //so the sensor will detect differences above 1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(back7Days > currentDate){
                    break;
                }
            }
            csvReader.close();
        }

        channel.close();
        conn.close();
    }
}
