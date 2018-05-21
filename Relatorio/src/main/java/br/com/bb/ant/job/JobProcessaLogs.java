package br.com.bb.ant.job;

import java.util.TimerTask;

import br.com.relatorio.bo.Controller;

public class JobProcessaLogs extends TimerTask {

	@Override
	public void run() {
		System.out.println("PROCESSANDO...");
		Controller controller = new Controller();
		controller.coletarLogsApache();
	}
}
