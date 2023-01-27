package ru.glebpad.apidata.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glebpad.apidata.DBСonnector;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class SearchService {

    private DBСonnector dbСonnector;

    @Autowired
    public SearchService(DBСonnector dbСonnector) {
        this.dbСonnector = dbСonnector;
        dbСonnector.getDBConnection();
    }


    public void handleRequest() throws SQLException {
        String request = new String("Компьютерная техника");
        ArrayList<String> inns = new ArrayList<>(Arrays.asList("7717149663", "5009062762", "7706202481", "00000000"));
        PreparedStatement properCase = null;

        if (inns.equals(null)) {
            properCase = dbСonnector.getDBConnection().prepareStatement("SELECT * FROM main_no_inns(?);");
            properCase.setString(1, request);
            ResultSet result = properCase.executeQuery();

            while (result.next()) {
                System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " " + result.getString(4) + " " + result.getString(5));
            }

        } else {
            properCase = dbСonnector.getDBConnection().prepareStatement("SELECT * FROM main_inns(?,?);");
            System.out.println(inns);
            properCase.setArray(1, dbСonnector.getDBConnection().createArrayOf("VARCHAR", inns.toArray(new String[0])));
            properCase.setString(2, request);
            ResultSet result = properCase.executeQuery();

            while (result.next()) {
                System.out.println(result.getString(1) + " " + result.getString(2) + " " + result.getString(3) + " " + result.getString(4) + " " + result.getString(5));
            }
        }

    }


}
