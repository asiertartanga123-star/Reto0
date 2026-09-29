package Util;
import java.io.*;

/**
 * Flujo para añadir objetos a un fichero serializado sin escribir otra cabecera.
 * Debe usarse al abrir un flujo de salida en modo append.
 */
public class AñadirObjetoSinCabecera extends ObjectOutputStream{
	
		/** Reinicia el estado del flujo sin duplicar la cabecera del fichero. */
		protected void writeStreamHeader() throws IOException {
		 reset();	
	 }

		/** Crea el flujo sin destino asociado. */
	public AñadirObjetoSinCabecera () throws IOException{ 
		super();
		}
		/** Crea el flujo que escribe en el destino indicado. */
		public AñadirObjetoSinCabecera(OutputStream out) throws IOException{
		super(out);
		}
	}
