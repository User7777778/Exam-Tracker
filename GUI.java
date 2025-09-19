import ecs100.*;
import java.awt.Color;
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GUI
{
    // instance variables
    private ExamTracker tracker; // link
    private String highlightedExamTitle = null; // stores title to higlight

    /**
     * Constructor for objects of class GUI
     */
    public GUI()
    {
        // initialise instance variables
        tracker = new ExamTracker();    // create your exam tracker
        UI.initialise();
        // buttons
        UI.addButton("Add Exam", tracker::addExamFromInput);
        UI.addButton("Find Exam", tracker::findExamFromInput);
        UI.addButton("Show all exams", this::drawExamsVisual);
        UI.addButton("Delete Exam", tracker::deleteExamFromInput);
        UI.addButton("Toggle Details", tracker::toggleAllDetails);
        UI.addButton("Change Status", tracker::changeExamStatus);
        UI.addButton("Sort Exams", tracker::showExamsByStatus);
        UI.addButton("Quit", UI::quit);
        
        
        
    }
    
    public void drawExamsVisual() {
        UI.clearGraphics(); // clears the canvas
        UI.clearText(); // clear thing
        
        int x = 50; // starting x
        int y = 50; // starting y
        int width = 200;
        int height = 80;
        int gap = 20; // space between cards

        for (Exam exam : tracker.getAllExams().values()) {
            // highlighter loop
            if (highlightedExamTitle != null &&
                exam.getTitle().equalsIgnoreCase(highlightedExamTitle)) {
                UI.setColor(Color.RED); //border  
            } else {
                UI.setColor(Color.BLACK); // not highlighted border
            }
            
            // draw a rectangle maybe? testing
            UI.drawRect(x, y, width, height);
            // text in rectangle maybe? 
            UI.drawString("Title: " + exam.getTitle(), x + 10, y + 20);
            UI.drawString("Date: " + exam.getDate(), x + 10, y + 40);
            UI.drawString("Time: " + exam.getTime(), x + 10, y + 60);

            y += height + gap;
        }
    }
    
    
    
    /**
     * GUI's main method
     */
    public static void main(String[] args) {
        new GUI();
    }
    
}
