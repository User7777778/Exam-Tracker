import ecs100.*;
import java.util.HashMap;
import java.awt.Color;
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
    private Exam highlightedExam = null;

    /**
     * Constructors
     */
    public ExamTracker() {
        collection = new HashMap<Integer, Exam>();
        this.currExamId = 0;
        
        // GUI stuff
        UI.initialise();
        UI.addButton("Add exam", this::addExamFromInput);
        UI.addButton("Find exam", this::findExamFromInput);
        UI.addButton("Show all exams", this::printAll);
        UI.addButton("Delete exam", this::deleteExamFromInput);
        UI.addButton("Toggle All Exams", this::toggleAllDetails);
        UI.addButton("Change status", this::changeExamStatus);
        UI.addButton("Quit", UI::quit);
        
        drawExams();
        
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
    public boolean addExam(String title, String date, String time, String location) {  // core class
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
    public void addExamFromInput() {  // console (separation of concerns)
        UI.clearText();
        String title = UI.askString("Enter Exam title: ");
        String date = UI.askString("Enter Exam date (yyy-mm-dd): ");
        String time = UI.askString("Enter Exam Time (morn/aftern): ");
        String location = UI.askString("Enter Exam location: ");
        String statusInput = UI.askString("Enter readiness (ready / moderate / not): ");
        
        boolean added = addExam(title, date, time, location);
        if (added) {
            Exam addedExam = collection.get(currExamId);
            if (statusInput.equalsIgnoreCase("ready")) {
                addedExam.setStatus(Exam.READY);
            } else if (statusInput.equalsIgnoreCase("moderate")) {
                addedExam.setStatus(Exam.MODERATELY_READY);
            } else {
                addedExam.setStatus(Exam.NOT_READY);
            }
            UI.println("Exam added: " + title);
        } else {
            UI.println("Exam not added.");
        }
        
        drawExams();
    }
    
    /**
     * exma status changer
     */
    public void changeExamStatus() {
        String title = UI.askString("Enter exam title to update status: ");
        if (findExam(title)) {
            String statusInput = UI.askString("Enter new status (ready / moderate / not): ");
            if (statusInput.equalsIgnoreCase("ready")) {
                currExam.setStatus(Exam.READY);
            } else if (statusInput.equalsIgnoreCase("moderate")) {
                currExam.setStatus(Exam.MODERATELY_READY);
            } else {
                currExam.setStatus(Exam.NOT_READY);
            }
            UI.println("Status updated for " + currExam.getTitle());
        } else {
            UI.println("Exam not found");
        }
        drawExams();
    }
    
    /**
     * finds exam by title and date
     */
    public boolean findExam(String title) {
        for (Exam exam : collection.values()) {
            if (exam.getTitle().equalsIgnoreCase(title)) {
                currExam = exam;
                highlightedExam = exam;
                return true;
            }
        }
        currExam = null;
        highlightedExam = null;
        return false;
    }
    
    /**
     * finds and display exam from user input
     */
    public void findExamFromInput() {
        UI.clearText();
        String title = UI.askString("Enter Exam Title: ");
        if (findExam(title)) {
            UI.println("Exam found");
            currExam.displayExam();
            highlightedExam = currExam;
        } else {
            UI.println("Exam not found.");
            highlightedExam = null;
        }
        drawExams();
    }
    
    /**
     * Deletes an exam
     */
    public boolean deleteExam(String title) {
        for (int id : collection.keySet()) {
            Exam exam = collection.get(id);
            if (exam.getTitle().equalsIgnoreCase(title)) {
                if (highlightedExam == exam) highlightedExam = null;
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
        UI.clearText();
        String title = UI.askString("Enter Exam Title to delete: ");
        if (deleteExam(title)) {
            UI.println("Exam deleted,");
        } else {
            UI.println("Exam not found.");
        }
        drawExams();
    }
    
    /**
     * prints all exams
     */
    public void printAll() {
        UI.clearText();
        if (collection.isEmpty()) {
            UI.println("No exams in the collection.");
            return;
        }
        
        for (Exam exam : collection.values()) {
            exam.displayExam();  
            UI.println("----------------------");
        }
    }
    
    /**
     * toggles details for all exams
     */
    public void toggleAllDetails() {
        for (Exam exam : collection.values()) {
            exam.toggleDetails();
        }
        UI.println("Details toggled for all exams");
        drawExams();
    }
    
    /**
     * exam drawing method
     */
    public void drawExams() {
        UI.clearGraphics(); 
        int x = 50, y = 50;
        int width = 200, height = 80;
        int gap = 20;

        for (Exam exam : collection.values()) {
            // set border color
            if (exam == highlightedExam) UI.setColor(Color.RED);
            else UI.setColor(Color.BLACK);

            UI.drawRect(x, y, width, height); // draw rectangle

            // draw text
            UI.setColor(Color.BLACK);
            UI.drawString("Title: " + exam.getTitle(), x + 10, y + 20);
            if (exam.isShowingDetails()) {
                UI.drawString("Date: " + exam.getDate(), x + 10, y + 40);
                UI.drawString("Time: " + exam.getTime(), x + 10, y + 60);
            }
            
            // status bar
            if (exam.getStatus() == Exam.READY) {
                UI.setColor(Color.GREEN);
            } else if (exam.getStatus() == Exam.MODERATELY_READY) {
                UI.setColor(Color.ORANGE);
            } else {
                UI.setColor(Color.RED);
            }
            UI.fillRect(x, y + height - 10, width, 10);

            y += height + gap;
        }
    }
    
    /**
     * exam sorting system
     */
    public void showExamsByStatus() {
    String statusInput = UI.askString("Enter status to show (all / ready / moderate / not): ");
    UI.clearGraphics();
    UI.clearText();
    int x = 50, y = 50;
    int width = 200, height = 80;
    int gap = 20;

    for (Exam exam : collection.values()) {
        boolean show = false;

        if (statusInput.equalsIgnoreCase("all")) {
            show = true;
        } else if (statusInput.equalsIgnoreCase("ready") && exam.getStatus() == Exam.READY) {
            show = true;
        } else if (statusInput.equalsIgnoreCase("moderate") && exam.getStatus() == Exam.MODERATELY_READY) {
            show = true;
        } else if (statusInput.equalsIgnoreCase("not") && exam.getStatus() == Exam.NOT_READY) {
            show = true;
        }

        if (show) {
            UI.setColor(Color.BLACK);
            UI.drawRect(x, y, width, height);
            UI.drawString("Title: " + exam.getTitle(), x + 10, y + 20);
            UI.drawString("Date: " + exam.getDate(), x + 10, y + 40);
            UI.drawString("Time: " + exam.getTime(), x + 10, y + 60);

            // status bar
            if (exam.getStatus() == Exam.READY) {
                UI.setColor(Color.GREEN);
            } else if (exam.getStatus() == Exam.MODERATELY_READY) {
                UI.setColor(Color.ORANGE);
            } else {
                UI.setColor(Color.RED);
            }
            UI.fillRect(x, y + height - 10, width, 10);

            y += height + gap;
        }
    }
}
    

    /**
     * Returns all exams as a collection
     */
    public HashMap<Integer, Exam> getAllExams() {
        return collection;
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
            UI.println("(T)oggle Details On/Off");
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
            } else if (choice.equalsIgnoreCase("T")) {
            toggleAllDetails();
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






