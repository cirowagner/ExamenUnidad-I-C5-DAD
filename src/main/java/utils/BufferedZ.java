package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedZ {

    public BufferedZ(){
    }

    BufferedReader readK=new BufferedReader(new InputStreamReader(System.in));

    public int read(int date, String message){
        System.out.println(message);
        try {
            date=Integer.parseInt(readK.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    public String read(String date, String message){
        System.out.println(message);
        try {
            date=readK.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    public double read(double date, String message){
        System.out.println(message);
        try {
            date=Double.parseDouble(readK.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    public char read(char date, String message){
        System.out.println(message);
        try {
            date=readK.readLine().toUpperCase().charAt(0);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }
}
