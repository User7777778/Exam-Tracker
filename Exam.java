import ecs100.*;
/**
 * Exam class for Exam tracker
 *
 * NVP
 * @version 10/07/2025
 */
public class Exam
{
    // instance variables
    private int id;
    private String title;
    private String date;
    private String time;
    private String location;
    
    private boolean showDetails = true; //for later GUI (toggle)
    
    private int locX = 100;
    private int locY = 100;
    private static final double WIDTH = 250;
    private static final double HEIGHT = 120;

    /**
     * Constructor for objects of class Exam
     */
    public Exam(int id, String title, String date, String time, String location)
    {
        // initialise instance variables
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    
    /**
     * gets the ID of the exam
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Gets title of the exam
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * gets the date of the exam
     */
    public String getDate() {
        return this.date;
    }
    
    /**
     * gets the time of the exam
     */
    public String getTime() {
        return this.time;
    }
    
    /** 
     * gets the location of the exam
     */
    public String getLocation() {
        return this.location;
    }
    
    /**
     * checks whether details are toggled
     */
    public boolean isShowingDetails() {
        return this.showDetails;
    }
    
    /**
     * rtoggle details on and off
     */
    public void toggleDetails() {
        this.showDetails = !this.showDetails;
    
    }
    
    /**
     * display exam details
     */
    public void printDetails() {
        UI.println("ID: " + id);
        UI.println("TItle: " + title);
        UI.println("Date: " + date);
        UI.println("Time: " + time);
        UI.println("Location: " + location);

    }

}







