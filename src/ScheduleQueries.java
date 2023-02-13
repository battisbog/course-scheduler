/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author aryanpatil
 */

public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry schedule)
    {
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values ( ?,?,?,?,?)");
            addScheduleEntry.setString(1, schedule.getSemester());
            addScheduleEntry.setString(2, schedule.getStudentID());
            addScheduleEntry.setString(3, schedule.getCourseCode());
            addScheduleEntry.setString(4, schedule.getStatus());
            java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            addScheduleEntry.setTimestamp(5, timestamp);
            
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) 
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select (coursecode, studentid, status) from app.schedule where semester = ?, studentid = ?");
            
            resultSet = getScheduleByStudent.executeQuery();
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                schedule.add(new ScheduleEntry(semester, resultSet.getString(1),resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
        
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode ){
     
        int count = 0;
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            
            resultSet = getScheduledStudentCount.executeQuery();
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            
            count = resultSet.getInt(1);
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return count;
        
    }
    
    
}
