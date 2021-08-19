package com.example.automation2.dao;

import com.example.automation2.model.User;
import com.example.automation2.util.DatabaseJDBC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*** Created: 8/14/2021 --- 1:50 AM --- ALPHANULL ***/

@Repository
public class UserDAO implements IUserDAO {

    private final Connection connection;
    private final String nameTable = "tabUsers";
    private final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO() {
        this.connection = DatabaseJDBC.getConnection();
    }

    // Gets PreparedStatement and User, designed for filling ? marks in sql strings
    private void setStatementAttributes
            (PreparedStatement statement, User user, boolean useUUID) {
        try {
            int index = 1;
            if(useUUID) statement.setString(index++,user.getUuid().toString());
            statement.setString (index++,user.getUserName());
            statement.setString (index++,user.getUserEmail());
            statement.setInt    (index++,user.getUserAge());
            statement.setString (index++,user.getUserGenderText());
            statement.setBoolean(index,user.getActive());
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    // Gets ResultSet and Target User, designed for returning an Optional<User>.of
    // This function called in getUserByUUID() func and fills target user attrs
    private void setUserAttributes(@NonNull ResultSet set, @NonNull User userTemp) {
        try {
            userTemp.setUuidText(set.getString("uuid"));
            userTemp.setUserName(set.getString("username"));
            userTemp.setUserEmail(set.getString("useremail"));
            userTemp.setUserAge(set.getInt("userage"));
            userTemp.setUserGenderText(set.getString("usergender"));
            userTemp.setActive(set.getBoolean("isactive"));
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    // Add new User into table in owned database
    @Override
    public int addUser(@NonNull UUID uuid, @NonNull User user) {
        try {
            String sql = "INSERT INTO "+nameTable+
                    " (uuid,username,useremail,userage,usergender,isactive) "+
                    " VALUES (?,?,?,?,?,?) ";
            PreparedStatement statement = connection.prepareStatement(sql);
            setStatementAttributes(statement,user,true);
            boolean resInsert = statement.execute();
            if(!resInsert) {
                logger.error(" --- ERROR --- addUser -> UserDAO \n");
                return -1;
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
            return -1;
        } return 1;
    }

    // Gets an Optional User by a ResultSet of a sql Statement
    @NonNull
    private Optional<User> getUserFromResultSet(@NonNull User userTemp,@NonNull ResultSet set) {
        setUserAttributes(set, userTemp);
        return Optional.of(userTemp);
    }

    // Gets an Common User by a ResultSet of a sql Statement
    @NonNull
    private User getUserFromResultSet(@NonNull ResultSet set) {
        User userTemp = new User();
        setUserAttributes(set, userTemp);
        return userTemp;
    }

    // Returns Optional.of(targetUser) by its UUID attr
    @Override
    public Optional<User> getUserByUUID(@NonNull UUID uuid) {
        Optional<User> optionalUser = Optional.empty();
        try {
            String sql = "SELECT * FROM "+nameTable+" WHERE uuid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,String.valueOf(uuid));
            ResultSet set = statement.executeQuery();
            if(set.next())
                optionalUser = getUserFromResultSet(new User(),set);
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return optionalUser;
    }

    // Returns all user rows stored in database as an ArrayList<User>
    List<User> userList = new ArrayList<>();

    @Override
    public List<User> getUserAll() {
        userList.clear();
        try {
            String sql = "SELECT * FROM "+nameTable;
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // Takes Column should be ordered and ordering mode (ASC or DESC)
    public List<User> getUserAllOrdered(@NonNull String column,final boolean asc) {
        userList.clear();
        try {
            String orderMode = (asc) ? "ASC" : "DESC";
            String sql = "SELECT * FROM "+nameTable+" ORDER BY "+column+" "+orderMode;
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // Takes Column should be filtered and state of tha col (WHERE column='state')
    public List<User> getUserAllFiltered(@NonNull String column, @NonNull String state) {
        userList.clear();
        try {
            String sql = "SELECT * FROM "+nameTable+" WHERE "+column+"="+"'"+state+"'";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // Just like previous function with one difference
    // State arg will place in sql command without any (')s
    // Helpful for null check and if user input in jsp file is empty
    public List<User> getUserAllFilteredNoCot(@NonNull String column, @NonNull String state) {
        userList.clear();
        try {
            String sql = "SELECT * FROM "+nameTable+" WHERE "+column+"="+state;
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // Just like filtering columns but this function will do this by LIKE strategy
    public List<User> getUserAllLiked(@NonNull String column, @NonNull String state) {
        userList.clear();
        try {
            String sql = "SELECT * FROM "+nameTable+" WHERE "+column+
                    " LIKE "+"'%"+state+"%'";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // For complicated sql statements and commands
    // Helpful for multiFiltered results
    public List<User> getUserAllComplex(@NonNull String sql) {
        userList.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                User userTemp = getUserFromResultSet(set);
                userList.add(userTemp);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return userList;
    }

    // Updates a user where given user.uuid equals to target one and attrs will be replace
    @Override
    public int updateUser(@NonNull User user) {
        int resUpdate = 0;
        try {
            String sql = "UPDATE "+nameTable+" SET " +
                    "username=?,useremail=?,userage=?,usergender=?,isactive=? " +
                    "WHERE uuid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            setStatementAttributes(statement,user,false);
            statement.setString(6,user.getUuidText());
            resUpdate = statement.executeUpdate();
            if(resUpdate != 1) logger.error(" --- ERROR --- updateUser -> UserDAO \n");
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return resUpdate;
    }

    // Gives uuid and drop every users in database with similar given uuid
    @Override
    public int dropUser(@NonNull UUID uuid) {
        int resDrop = 0;
        try {
            String sql = "DELETE FROM "+nameTable+" WHERE uuid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid.toString());
            resDrop = statement.executeUpdate();
            if(resDrop != 1) logger.error(" --- ERROR --- dropUser -> UserDAO \n");
        } catch(SQLException exception) {
            exception.printStackTrace();
        } return resDrop;
    }
}


