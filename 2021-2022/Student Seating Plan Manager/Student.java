/**
 * [Student.java]
 * This class represents a student attending a club.
 * 
 * @author Edison Du
 * @author Peter Gu
 * @author Jeffrey Xu
 */
public class Student {
    private String name;
    private int id;
    private int grade;
    private String group;
    private int[] friendPreferences = new int[3];
    
    /**
     * Student
     * Constructs a new student object
     * @param name the student's name
     * @param id the student's identification number
     * @param grade the student's grade
     * @param friends an array containing the id of the student's friends
     * @param group the group in the club that the student belongs to
     */
    public Student(String name, int id, int grade, int[] friends, String group) {
        this.name = name;
        this.id = id;
        this.grade = grade;
        this.friendPreferences = friends;
        this.group = group;
    }
    
    /**
     * getId
     * This method returns the id of the student
     * @return the id of the student
     */
    public int getId() {
        return this.id;
    }

    /**
     * setId
     * Changes the id of the student
     * @param id the id of the student to change to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getName
     * Returns the name of the student
     * @return the name of the student
     */
    public String getName() {
        return this.name;
    }

        
    /**
     * setName
     * Changes the name of the student
     * @param name the name of the student to change to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getGrade
     * Returns the grade of the student
     * @return the grade of the student
     */
    public int getGrade() {
        return this.grade;
    }

    /**
     * setGrade
     * Changes the grade of the student
     * @param grade the grade of the student to change to
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * getGroup
     * Returns the group the student belongs to
     * @return the group the student belongs to
     */
    public String getGroup() {
        return this.group;
    }
    
    /**
     * setGroup
     * Changes the group the student belongs to
     * @param group the group to change to
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * getFriendPreferences
     * Returns the array of friend preferences
     * @return the array of friend preferences
     */
    public int[] getFriendPreferences() {
        return this.friendPreferences;
    }
}