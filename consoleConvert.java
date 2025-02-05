package poundConverter;

import java.util.Scanner;

//This function takes a pound value from user and converts it to kilograms
public class consoleConvert {
	consoleConvert(){
	}
	protected static double poundConvert(double pounds){
        double kg = pounds / 2.205;
        return kg;
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter weight in pounds: ");
        double pounds = scanner.nextDouble();
        double kilograms = poundConvert(pounds);
        System.out.printf(pounds + " pounds is equal to %.2f kilograms.", kilograms);
        scanner.close();
    }	
}
