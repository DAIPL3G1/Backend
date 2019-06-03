package com.saferus.backend;

import com.saferus.backend.repository.BindRepository;
import com.saferus.backend.repository.TripRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import java.util.Date;
import java.util.Timer;
import org.springframework.context.ApplicationContext;

@ComponentScan(basePackages = "com.saferus.backend")
@SpringBootApplication()
public class BackendApplication extends SpringBootServletInitializer {

    private static Class applicationClass = BackendApplication.class;
    
    public static MyTimeTask task;

    public static void main(String[] args) throws ParseException {

        ApplicationContext appContext = SpringApplication.run(BackendApplication.class, args);

        //the Date and time at which you want to execute
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter.parse("2019-05-03 11:30:00");

        //Now create the time and schedule it
        Timer timer = new Timer();
        

        task = new MyTimeTask(appContext);
        
        //Use this if you want to execute it repeatedly
        int period = 60000*60;//60secs*60=1h
        timer.schedule(task, date, period);
        
       /* MyTimeTask hello = new MyTimeTask(appContext);
        hello.run();*/
    }

}
