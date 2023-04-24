/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_project;
import java.text.ParseException;
import java.util.TimerTask;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author DroNN4e$$$$$ &&&&&& DANATELLO VELIKIY
 */
public class MyTimer extends TimerTask  {                                       //Добавить в класс функции запуска всех таймеров

    
    private long ex_time = 0;
    private TimerFrame window;
    private static boolean flag = false;
    private String name;
    private String date_time;
    private String title;
    
    public void setFlag(boolean a){
        flag = a;
    }

    public TimerFrame getWindow() {
        return window;
    }

    public void setWindow(TimerFrame window) {
        this.window = window;
    }
    
    public MyTimer(String name, String date_time, String title) {
        this.window = new TimerFrame();
        this.name = name;
        this.date_time = date_time;
        this.title = title;
    }

    public MyTimer() {
        this.window = new TimerFrame();
    }
    
    public long getEx_time() {
        return ex_time;
    }

    public void setEx_time(long ex_time) {
        this.ex_time = ex_time;
    }
    
@Override
public void run() {
        try {
            completeTask();
        } catch (ParseException ex) {
            Logger.getLogger(MyTimer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

private void completeTask() throws ParseException {
        try {
            DB_helper helper = new DB_helper();
            flag = true;
            helper.change_labels(name, date_time, flag);
            //Возможно сюда добавить check()
            window.clock_start(title);
            Thread.sleep(ex_time);
            window.clock_finish(title);
            flag = false;
            helper.change_labels(name, date_time, flag);
        } catch (InterruptedException e) {
        }
    }

}