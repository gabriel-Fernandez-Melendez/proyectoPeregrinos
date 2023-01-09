/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gabof
 */
public class Validadores {
    
    
    public static boolean validarNacionalidad(String nacionalidad) {
		boolean ret = false;
		File fichero = new File("nacionalidad.txt");
		FileReader lector = null;
		BufferedReader buffer = null;
		try {
			try {
				lector = new FileReader(fichero);
				buffer = new BufferedReader(lector);
				String linea;
				while ((linea = buffer.readLine()) != null) {
					String[] campos = linea.split("\\|");
					String campo = campos[0];
					if (nacionalidad.equalsIgnoreCase(campo)){
                                            return true;
                                        }
                                        
							
				}
			} finally {
				if (buffer != null) {
					buffer.close();
				}
				if (lector != null) {
					lector.close();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Se ha producido una FileNotFoundException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Se ha producido una IOException" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Se ha producido una Exception" + e.getMessage());
		}
		return ret;
	}
    
    public static boolean validarNombre(String nombre) {
		// regEx general para cadena de caracteres con longitud entre 1 y 50 caracteres,
		// aceptando dígitos, letras MAYUS y minúsculas, con tildes, diréresis y
		// diferentes símbolos especiales
		// Pattern patron = Pattern.compile("[
		// 0-9A-Za-zñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ¡!¿?@#$%()=+-€/.,]{1,50}");
		Pattern patron = Pattern.compile("[ A-Za-zñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ-]{3,50}");
		Matcher comprobacion = patron.matcher(nombre);
		return comprobacion.matches();//
	}
    
    	public static boolean leerBoolean() {
		boolean ret;
		Scanner in;
		char resp;
		do {
			System.out.println("Pulse s para Sí o n para No");
			in = new Scanner(System.in, "ISO-8859-1");
			in.reset();
			resp = in.nextLine().charAt(0);
			if (resp != 's' && resp != 'S' && resp != 'n' && resp != 'N') {
				System.out.println("Valor introducido incorrecto.");
			}
		} while (resp != 's' && resp != 'S' && resp != 'n' && resp != 'N');
		if (resp == 's' || resp == 'S') {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}
        
        public static boolean validarFechaNueva(LocalDate fecha) {
            boolean val=false;
            LocalDate primerPEREGRINO=LocalDate.of(1800, Month.MARCH, 14);
		if(fecha.isBefore(LocalDate.now()) || fecha.isAfter(primerPEREGRINO)){
                    val=true;
                }
                else{
                    val=false;
                }
	return val;	
	}
}
