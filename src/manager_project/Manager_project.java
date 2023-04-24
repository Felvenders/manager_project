/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package manager_project;

import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author DroNN4e$$$$$ &&&&&& DANATELLO VELIKIY
 */
public class Manager_project {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     * @throws java.lang.InterruptedException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws ParseException, InterruptedException, ClassNotFoundException, ClassNotFoundException, SQLException {
        //Запускаем GUI
        main_frame window = new main_frame();
        window.setResizable(false);                                             //Делаем окно неизменяемым
        window.setLocationRelativeTo(null);                                     //Делаем окно по центру
        window.Initial();                                                       //Инициализируем таблицу
        window.Start_Name();                                                    //Запускаем ввод имени
    }
}
