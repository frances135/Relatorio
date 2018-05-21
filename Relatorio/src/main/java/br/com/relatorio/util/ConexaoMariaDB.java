package br.com.relatorio.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ConexaoMariaDB {

	private static final DataSource dataSource ;
	
    static {
        try {
        	InitialContext context = new InitialContext();
        	
        	dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql");

        } catch (Throwable ex) {
        	System.out.println("ERRO AO CONECTAR COM MYSQL: " + ex.getMessage());
        	ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static DataSource getDatasource() {
		return dataSource;
	}

	public static DataSource getConnection() {
		return dataSource;
	}

    /**
     * <Context docBase="Relatorio" path="/Relatorio" reloadable="true"
					source="org.eclipse.jst.jee.server:Relatorio" crossContext="true" debug="1">
				
					<Resource name="jdbc/mysql" auth="Container" type="javax.sql.DataSource"
							maxActive="50" maxIdle="30" maxWait="10000" username="root"
							password="" driverClassName="com.mysql.jdbc.Driver"
							url="jdbc:mysql://localhost:3306/relatorio" />
				</Context>
     */

}