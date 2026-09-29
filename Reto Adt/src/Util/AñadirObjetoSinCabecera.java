/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author Asier.Prieto
 */
public class AñadirObjetoSinCabecera extends ObjectOutputStream{
	
		//Sobrescribimos el método que crea la cabecera 
                @Override
		protected void writeStreamHeader() throws IOException {
		 reset();	
	 }

		//Constructores
	public AñadirObjetoSinCabecera () throws IOException{ 
		super();
		}
		public AñadirObjetoSinCabecera(OutputStream out) throws IOException{
		super(out);
		}
	}

