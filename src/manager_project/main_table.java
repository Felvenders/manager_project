/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DroNN4e$$$$$ &&&&&& DANATELLO VELIKIY
 */
class main_table {                                                              //Добавить в класс функции открытия потока к бд + забора данных
    
    private String title;
    private String date_time;
    private String ex_time;
    private String tag;
    private String priority;
    private String task_labels;
    private String comment_;
    private String users_name;
    private Date date = null;
    private Date exec_time = null;
    private static int time_ms = 0;
    
    //Функция получения миллисекунд из времени
    public int getTime_ms(){
        time_ms = exec_time.getSeconds() * 1000;
        time_ms = time_ms + exec_time.getMinutes() * 1000 * 60;
        time_ms = time_ms + exec_time.getHours() * 1000 * 60 * 60;
        return time_ms;
    }
    
    public Date getDate(){
        return this.date;
    }
    
    public Date exec_time(){
        return this.exec_time;
    }
    
    //Проверка даты и времени по сравнению с текущим днем
    public boolean check_date_time(){
        return (!date.before(new Date()));
    }
    
    //Проверка метки задачи: (Ожидает, выполняется, выполнена)
    public boolean check_task_label(){
        return task_labels.equals("Ожидает");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_time() {
        return date_time;
    }

    //Переводим String в дату
    public void setDate_time(String date_time) throws ParseException {
        this.date_time = date_time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");          //Формат для преобразования даты и времени
        date_time = date_time.replace(' ', 'T');
        date = simpleDateFormat.parse(date_time);
    }

    public String getEx_time() {
        return ex_time;
    }

    public void setEx_time(String ex_time) throws ParseException {
        this.ex_time = ex_time;
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        exec_time = simpleTimeFormat.parse(this.ex_time);
    }
    
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTask_labels() {
        return task_labels;
    }

    public void setTask_labels(String task_labels) {
        this.task_labels = task_labels;
    }

    public String getComment_() {
        return comment_;
    }

    public void setComment_(String comment_) {
        this.comment_ = comment_;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    @Override
    public String toString() {
        return title + " " + date_time + " " + tag + " " + priority + " " + task_labels + " " + comment_ + " " + users_name;
    }
}
