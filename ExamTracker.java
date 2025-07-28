import ecs100.*;
import java.util.HashMap;
/**
 * Write a description of class ExamTracker here.
 *
 * NVP
 * @version 19/07/2025
 */
public class ExamTracker
{
    // instance variables - replace the example below with your own
    private HashMap<Integer, Exam> collection;
    private int currExamId;
    private Exam currExam;

    /**
     * Constructors
     */
    public ExamTracker() {
        collection = new HashMap<Integer, Exam>();
        this.currExamId = 0;
    }
    
    /**
     * sets exam ID
     */
    public void setExamId() {
        this.currExamId += 1;
    }
    
    /**
     * adds new exam
     */
    public boolean addExam(String title, String date, String time, String location) {
        for (Exam exam : collection.values()) {
            if (exam.getTitle().equalsIgnoreCase(title) &&
            exam.getDate().equalsIgnoreCase(date)) {
                UI.println("Exam already exists");
                return false;
            }
        }
        
        setExamId();
        Exam newExam = new Exam(currExamId, title, date, time, location);
        collection.put(currExamId, newExam);
        return true;
    }
    
    /**
     * asks user for deatils
     */
    public void addExamFromInput() {
        String title = UI.askString("ENter Exam title: ");
        String date = UI.askString("Enter Exam date (yyy-mm-dd): ");
        String time = UI.askString("Enter Exam Time (morn/aftern): ");
        String location = UI.askString("Enter Exam location: ");
        
        boolean added = addExam(title, date, time, location);
        if (added) {
            UI.println("Exam added: " + title);
        } else {
            UI.println("Exam not added.");
        }
    }
    
    /**
     * finds exam by title and date
     */
    public boolean findExam(String title, String date) {
        for (Exam exam : collection.values()) {
            if (exam.getTitle().equalsIgnoreCase(title) &&
            exam.getDate().equalsIgnoreCase(date)) {
                currExam = exam;
                return true;
            }
        }
        return false;
    }
    
    /**
     * finds and display exam from user input
     */
    public void findExamFromInput() {
        String title = UI.askString("Enter Exam Title: ");
        String date = UI.askString("Enter Exam Date (yyyy-mm-dd): ");
        if (findExam(title, date)) {
            UI.println("Exam found");
            UI.println(currExam.toString());
        } else {
            UI.println("Exam not found.");
        }
    }
    
    /**
     * Deletes an exam
     */
    public boolean deleteExam(String title, String date) {
        for (int id : collection.keySet()) {
            Exam exam = collection.get(id);
            if (exam.getTitle().equalsIgnoreCase(title) &&
            exam.getDate().equalsIgnoreCase(title) &&
            exam.getDate().equalsIgnoreCase(date)) {
                collection.remove(id);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Deletes an exam from user input
     */
    public void deleteExamFromInput() {
        String title = UI.askString("Enter Exam Title to delete: ");
        String date = UI.askString("Enter Exam Date (yyyy-mm=dd); ");
        if (deleteExam(title, date)) {
            UI.println("Exam deleted,");
        } else {
            UI.println("Exam not found.");
        }
    }
    
    /**
     * prints all exams
     */
    public void printAll() {
        if (collection.isEmpty()) {
            UI.println("No exams in the collection.");
            return;
        }
        
        for (Exam exam : collection.values()) {
            UI.println(exam.toString());  //sourced from: https://www.w3schools.com/jsref/jsref_tostring_string.asp
        }
    }
    
    /**
     * menu/console
     */
    public void menu() {
        String choice;
        do {
            UI.println("\n(A)dd Exam");
            UI.println("(F)ind Exam");
            UI.println("(P)rint All exams");
            UI.println("(D)elete Exam");
            UI.println("(Q)uit");
            
            choice = UI.askString("Enter a choice: ");
            
            if (choice.equalsIgnoreCase("A")) {
                addExamFromInput();
            } else if (choice.equalsIgnoreCase("F")) {
            findExamFromInput();
            } else if (choice.equalsIgnoreCase("P")) {
            printAll();
            } else if (choice.equalsIgnoreCase("D")) {
            deleteExamFromInput();
            } else if (choice.equalsIgnoreCase("Q")) {
            UI.println("Goodbye!");
            UI.quit();
            } else {
            UI.println("Not a valid.");
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }
    
    /**
     * Main method
     */
    public static void main (String[] args) {
        ExamTracker tracker = new ExamTracker();
        tracker.menu();
    }
}






