package com.frankmoley.lil.security.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.frankmoley.lil.security.util.DatabaseUtil;

public class PresidentDAO {

    // Implementação de PreparedStatement para evitar SQL Injection
    public List<President> getByLastName(String lastName){
        Connection connection = DatabaseUtil.getConnection();
        String sql = "select PRESIDENT_ID, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME, EMAIL_ADDRESS from PRESIDENT where LAST_NAME = ?";
        List<President> resultList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                resultList.add(processResultSet(resultSet));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return resultList;
    }


    private President processResultSet(ResultSet resultSet) throws SQLException{
        long id = resultSet.getLong("PRESIDENT_ID");
        String firstName = resultSet.getString("FIRST_NAME");
        String middleInitial = resultSet.getString("MIDDLE_INITIAL");
        String lastName = resultSet.getString("LAST_NAME");
        String emailAddress = resultSet.getString("EMAIL_ADDRESS");
        return new President(id, firstName, middleInitial, lastName, emailAddress);
    }


}
