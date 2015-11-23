/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.client;

/**
 *
 * @author benhur
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    static FileWriter f0;
	public static void write(String filename,String data) {
		try {
		    System.out.println("hehe "+ data);
//
//
//FileWriter f0 = new FileWriter(System.getProperty("user.dir")+"/"+filename);
//
//String newLine = System.getProperty("line.separator");
//
//
//    f0.write(data + newLine);
//
//f0.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

