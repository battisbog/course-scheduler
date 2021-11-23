/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aryanpatil
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudentNames;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (FIRSTNAME, LASTNAME, STUDENTID) values (?,?,?)");

            addStudent.setString(1, student.getFirstName());
            addStudent.setString(2, student.getLastName());
            addStudent.setString(3, student.getStudentID());
            
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        try
        {
            getAllStudents = connection.prepareStatement("select STUDENTID, FIRSTNAME, LASTNAME from app.STUDENT");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }

public static ArrayList<String> getStudentNames()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> studentNames = new ArrayList<>();
        try
        {
            getStudentNames = connection.prepareStatement("select FIRSTNAME, LASTNAME from app.STUDENT");
            resultSet = getStudentNames.executeQuery();
            
            while(resultSet.next())
            {
                studentNames.add(resultSet.getString(1)+' ' resultSet.getString(2));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentNames;
        
    }
    
    
}
