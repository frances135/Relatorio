package br.com.bb.relatorio.listener;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.bb.ant.job.JobProcessaLogs;

public class JobListener implements ServletContextListener {

	private Timer timerProcessaLogs;
	
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	
    	System.out.println("INICIALIZAÇÃO");
    	
		timerProcessaLogs = new Timer("processaLogs");
		
		timerProcessaLogs.scheduleAtFixedRate(new JobProcessaLogs(), 0, 5 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	System.out.println("CANCELAMENTO");
    	timerProcessaLogs.cancel();
    }
}