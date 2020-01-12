//package Serverhallpackage;
//
//import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import com.mysql.jdbc.Driver;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author Felix
// */
//public class ConnectionDatabase {
//   public static void main(String[] args) throws ClassNotFoundException{
//        Class.forName("com.mysql.jdbc.Driver");
//        
//        
//        
//        try{
//            
//            connectToAndQueryDatabase();
//        }
//        catch(Exception p){
//            p.printStackTrace();
//            System.out.println("Some thing whent wrong!");
//        }
//        
//        
//        
//        
//        
//        
//        
//        
//    }
//    
//    public static List<String> connectToAndQueryDatabase(){
//        String username = "root";
//        String password = "Hinsaringen39";
//        List<String> mylist = new ArrayList<>();
//        int id = 0;
//        int temp = 0;
//        int elf = 0;
//        int elk = 0;
//        
//        try (com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/systemintergration1database","root","Hinsaringen39");){
//           
//            
//            
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT id, temp, elforbrukning, elkostnad, FROM serverhall");
//            
//            while(rs.next()){
//                id = rs.getInt("id");
//                temp = rs.getInt("temp");
//                elf = rs.getInt("elforbrukning");
//                elk = rs.getInt("elkostnad");
//                
//                String sid = Integer.toString(id);
//                String stemp = Integer.toString(temp);
//                String self = Integer.toString(elf);
//                String selk = Integer.toString(elk);
//                
//                mylist.add(sid + stemp + self + selk);
//               
//            }
//            System.out.println(mylist);
//            
//            if(con != null){
//                System.out.println("Connected to database successfully");
//            } 
//           
//            
//
//            
//        }catch(Exception e){
//            System.out.println("not connected to database");
//        }
//        return mylist;
//    }
//    
//    
//
//    
//
//    
//}