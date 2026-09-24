/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;




/**
 *
 * @author Ricardo.Soza
 */
public class Ricardo {
    
    private Connection con;
    private PreparedStatement stmt;
    
    
    private void openConnection() {

		try {
			//con = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/futbol_americano?serverTimezone=Europe/Madrid&useSSL=false", "root",
				"abcd*1234");
		} catch (SQLException e) {
			System.out.println("Error al intentar abrir la BD");
		}
	}
    
}

