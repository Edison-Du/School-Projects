/**
 * [Main.java]
 * Launcher program
 * 
 * @author Edison Du
 * @author Peter Gu
 * @author Jeffrey Xu
 * @version 1.0 Oct 15, 2021
 */

public class Main{
    public static void main(String[] args) {

        String[] groups = {"Grp1", "Grp2", "Grp3"};
        FloorPlanSystem floorPlan = new FloorPlanSystem(groups);

        for (int i = 0; i < 20; i++) {
            Table table = new Table(i, 4);
            for (int j = 0; j < 4; j++) {
                table.addStudent(new Student(
                    "Student" + (i*4 + j), i*4 + j, 10, new int[]{}, groups[i%3])
                );
            }
            floorPlan.addTable(table, groups[i%3]);
        }

        floorPlan.displayTables();
    }
}