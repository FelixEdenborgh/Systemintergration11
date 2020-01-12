/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.Serverhallpackage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHallDBDao {

    Properties p = new Properties();

    public ServerHallDBDao() {
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            p.load(new FileInputStream("C:\\Users\\Felix\\OneDrive - System Provider Edenborgh AB\\Dokument\\NetBeansProjects\\Systemintergration1\\src\\java\\java\\Serverhallpackage\\settings.propeties"));
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHallDBDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerHallDBDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TempTable> getLatestTemperature() {

        List<TempTable> TempList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("SELECT id, temp, time FROM systemintergration1database.sampletable ORDER BY date DESC LIMIT 1");

            ResultSet rs = stmt.executeQuery();
             System.out.println("Connection Succes");

            while (rs.next()) {
                int id = rs.getInt("id");
                int temperature = rs.getInt("temp");
                Timestamp date = rs.getTimestamp("time");

                TempList.add(new TempTable(id, temperature, date));

            }
        } catch (Exception e) {
            e.printStackTrace();
             System.out.println("Connection notsucess");
        }

        return TempList;
    }

    public List<EconsumptionTable> getLatestConsumption() {

        List<EconsumptionTable> EconsList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("SELECT id, elforbrukning, time FROM systemintergration1database.sampletable ORDER BY date DESC LIMIT 1");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int econsumption = rs.getInt("elforbrukning");
                Timestamp date = rs.getTimestamp("time");

                EconsList.add(new EconsumptionTable(id, econsumption, date));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return EconsList;
    }

    public List<EpriceTable> getEprice() {

        List<EpriceTable> EpriceList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("SELECT id, elkostnad, time FROM systemintergration1database.sampletable ORDER BY date DESC LIMIT 1");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                int eprice = rs.getInt("elkostnad");
                Date date = rs.getDate("time");

                EpriceList.add(new EpriceTable(id, eprice, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EpriceList;

    }

    public void insertTemp(TempTable t) {

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO systemintergration1database.sampletable (temp) VALUES (?)");

            stmt.setInt(1, t.getTemperature());

            int a = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertEprice(EpriceTable t) {

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO systemintergration1database.sampletable (elkostnad) VALUES (?)");

            stmt.setInt(1, t.getEprice());

            int a = stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AvgTemperature> RapportAvgMinMax() {

        List<AvgTemperature> list = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("select avg(temp), max(temp), min(temp) from systemintergration1database.sampletable;");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int avgg = rs.getInt("avg(temp)");
                int maxx = rs.getInt("max(temp)");
                int minn = rs.getInt("min(temp)");

                list.add(new AvgTemperature(avgg, maxx, minn));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<AvgEconsumption> RapportAvgEcons() {

        List<AvgEconsumption> econList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))) {

            PreparedStatement stmt = con.prepareStatement("select avg(elforbrukning), max(elforbrukning), min(elforbrukning), elkostnad.elkostnad from systemintergration1database.elforbrukning, systemintergration1database.elkostnad");
                    
             ResultSet rs = stmt.executeQuery();
             
             while(rs.next()){
                 
                 int avg = rs.getInt("avg(elforbrukning)");
                 int min = rs.getInt("min(elforbrukning)");
                 int max = rs.getInt("max(elforbrukning)");
                 int eprice = rs.getInt("elkostnad");
                 
                 econList.add(new AvgEconsumption(avg, min, max, eprice));
                         
             }
             
        } catch(Exception e){
            e.printStackTrace();
        }
        return econList;
    }

}