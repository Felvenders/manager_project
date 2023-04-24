/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_project;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author xland
 */
public class DB_helper {

    private final String drvName = "org.postgresql.Driver";
    private final String user = "postgres";
    private final String pwd = "5432";     //postgres Для DANATELLO VELIKOGO
    private final String dbName = "jdbc:postgresql://localhost:5432/manager";
    private static main_frame window;

    public void setWindow(main_frame window) {
        DB_helper.window = window;
    }
    
    public DB_helper() {
    }

    //Функция добавления данных в БД
    public void add_data(String sql) throws ParseException {     
        try {
            Class.forName(drvName);
            Connection c = DriverManager.getConnection(dbName, user, pwd);
            Statement st = c.createStatement();
            //Передаем запрос
            boolean execute = st.execute(sql);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(main_frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Функция добавления данных в таблицу
    public ArrayList<main_table> take_data(String sql) throws ParseException {

        ArrayList<main_table> list;
        list = new ArrayList();

        try {
            Class.forName(drvName);
            try ( Connection c = DriverManager.getConnection(dbName, user, pwd)) {
                Statement st = c.createStatement();
                ResultSet rs = st.executeQuery(sql);
                //Достаем все данные с помощью цикла
                while (rs.next()) {
                    main_table main = new main_table();
                    main.setTitle(rs.getString(1));
                    main.setDate_time(rs.getString(2));
                    main.setEx_time(rs.getString(3));
                    main.setTag(rs.getString(4));
                    main.setPriority(rs.getString(5));
                    main.setTask_labels(rs.getString(6));
                    main.setComment_(rs.getString(7));
                    main.setUsers_name(rs.getString(8));
                    //Добавляем их в список
                    list.add(main);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Manager_project.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    //Функция удаления данных из таблицы
    public void delete_data(String sql, int index, String name) throws ParseException {
        ArrayList<main_table> list = new ArrayList<>();
        list = this.take_data(sql);

        int sec_index = 0;

        for (main_table main : list) {
            //Проверяем индексы
            if (sec_index == index) {
                String sql3 = "DELETE FROM main_table WHERE (title = '" + main.getTitle() + "') AND (date_time = '" + main.getDate_time() + "') AND (users_name = '" + name + "');";
                try {
                    Class.forName(drvName);
                    Connection c = DriverManager.getConnection(dbName, user, pwd);
                    Statement st = c.createStatement();
                    //Передаем запрос
                    boolean execute = st.execute(sql3);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(main_frame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            sec_index++;
        }
    }

    //Функция возвращающая данные, совпадающие по индексу с таблицей
    public main_table get_single_data(int index, String name, String sql) throws ParseException {
        ArrayList<main_table> list = new ArrayList<>();
        list = this.take_data(sql);
        main_table Single = new main_table();

        int sec_index = 0;

        for (main_table main : list) {
            //Проверяем индексы
            if (sec_index == index) {
                Single = main;
            }
            sec_index++;
        }
        return Single;
    }

    //Функция запуска таймера для задач с ожиданием
    public ArrayList<Timer> planned_start(String name) throws ParseException {
        //Планируем старт задач на будущее
        ArrayList<Timer> timer_list = new ArrayList();
        ArrayList<main_table> list = new ArrayList<>();
        String sql = "SELECT * FROM main_table WHERE " + "users_name = '" + name + "' ORDER BY date_time asc;";
        Date date = null;
        list = this.take_data(sql);

        if (!list.isEmpty()) {
            for (main_table main : list) {
                //Обработка даты и времени в нужный формат
                date = main.getDate();

                if (main.check_date_time()) {
                    System.out.println(date);
                    MyTimer timer_task = new MyTimer(main.getUsers_name(), main.getDate_time(), main.getTitle());
                    //Задание таймера
                    timer_task.setEx_time(main.getTime_ms());
                    Timer timer = new Timer(true);
                    timer_list.add(timer);
                    timer.schedule(timer_task, date);
                    //Запуск задачи в определенное время с помощью Даты и времени
                }
            }
        } else {
        }
        return timer_list;
    }

    //Функция запуска таймера для задач, которые уже начались
    public void emergency_start(String name) throws ParseException {
        //Экстренный старт задач, которые уже в процессе
        ArrayList<main_table> list = new ArrayList<>();
        Date date = null;
        String sql = "SELECT * FROM main_table WHERE " + "users_name = '" + name + "' ORDER BY date_time asc;";
        list = this.take_data(sql);

        if (!list.isEmpty()) {                                                  //Здесь будет запрос с датой начала раньше текущей
            for (main_table main : list) {
                date = main.getDate();
                //Сравниваем даты вручную
                if (!main.check_date_time()) {
                    long exec_time = main.getTime_ms() + date.getTime() - new Date().getTime();
                    if (exec_time > 0) {
                        System.out.println(date);
                        MyTimer timer_task = new MyTimer(main.getUsers_name(), main.getDate_time(), main.getTitle());
                        //Задание таймера
                        timer_task.setEx_time(exec_time);
                        Timer timer = new Timer(true);
                        timer.schedule(timer_task, 0);
                        //Запуск задачи в определенное время с помощью Даты и времени
                    }
                    else {                                                      //В таком случае просто меняем метки у этих задач
                        this.change_labels(main.getUsers_name(), main.getDate_time(), false);
                        
                    }
                }
            }
        } else {
        }
    }

    //Функция изменения состояния задачи (ожидает, выполняется, выполнена)
    public void change_labels(String name, String date_time, boolean flag) throws ParseException {
        //Передаем дату и время для точного перемещения задачи в текущее состояние
        String sql;
        if (flag){
            sql = "UPDATE main_table SET task_labels = 'в процессе' WHERE users_name = ('" + name + "') AND (date_time = '" + date_time + "');";
        } else {
            sql = "UPDATE main_table SET task_labels = 'выполнена' WHERE users_name = ('" + name + "') AND (date_time = '" + date_time + "');";
        }
        
        try {
            Class.forName(drvName);
            try ( Connection c = DriverManager.getConnection(dbName, user, pwd)) {
                Statement st = c.createStatement();
                boolean execute = st.execute(sql);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Manager_project.class.getName()).log(Level.SEVERE, null, ex);
        }
        window.check();
    }
}
