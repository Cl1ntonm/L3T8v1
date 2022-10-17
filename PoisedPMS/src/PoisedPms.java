
/**
 * Task 8 Compulsory Task
 *
 * Task Purpose - modify previous level 2 capstone project to work with MySql
 *
 * Program Description - Program performs the following functions for Project management
 *                       .Adds new Projects withe details - to a file  to be recalled later
 *                       .View all ongoing Projects at once
 *                       .View condensed list of ongoing Projects
 *                       .View List if of Projects passed it due date
 *                       .Search for and view a particular project
 *                       .Update any selected ongoing Project detail
 *                       .Delete any ongoing Project
 *                       .Finalize a project by adding completion date and moving it to a new file
 *                       .View condensed list of Completed Projects
 *                       .View an in Detail list of Completed Projects
 *
 * Class Breakdown - Person class , holds Person Objects
 *                 - Project class , holds Project Objects
 *                 - PoisedUtilities , holds reusable methods for the program
 *                 - CrudFunctions , holds methods that can Create, Read, Update and Delete
 *                 - SqlQuerieMethods , holds String SQL query's to be accessed as needed
 *
 * @author ClintonM
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.ListIterator;
import java.util.Scanner;


/**
 * Main class that executes the menu options
 */
public class PoisedPms {

    public static void main(String[] args) {

        //Connection to the Database
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/poisepms5?useSSL=false",
                    "otheruser",
                    "swordfish"
            );

            // Creating a direct line to the database
            Statement statement = connection.createStatement();
            ResultSet results = null;

            // declare base variables
            boolean closeProgram = false;
            ArrayList<Project> projectArrayList = new ArrayList<>();
            ListIterator listIterator = null;
            ArrayList<Integer> projectNumberList = new ArrayList();

            // while loop for menu
            while (!closeProgram){
                Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

                // calling method to display the options available for the user
                PoisedUtilities.menuOption();
                try {
                    switch (scanner.nextInt()) {
                        case 1:
                            //Add New Project details
                            CrudFunctions.addNewProjectDetails(scanner,results,statement,projectNumberList,connection);
                            break;

                        case 2:
                            //View Ongoing Projects - Full Details
                            CrudFunctions.viewOngoingProjectsWithDetails(projectArrayList,statement,listIterator);
                           break;

                        case 3:
                            //View Ongoing Projects - Name and Number
                            CrudFunctions.viewOngoingProjectsNumAndNameOnly(projectArrayList,statement,listIterator);
                            break;

                        case 4:
                            //View Ongoing Projects Passed Due Date
                            CrudFunctions.viewOngoingProjectsPassedDueDate(projectArrayList,statement,listIterator);
                            break;

                        case 5:
                            //Search for Ongoing Project
                            CrudFunctions.searchForOnGoingProject(scanner,projectArrayList,statement,listIterator);
                            break;

                        case 6:
                            //Update Ongoing Project Details
                            CrudFunctions.updateOngoingProjectDetails(scanner,projectArrayList,statement,listIterator,connection,results);

                            break;
                        case 7:
                            //Delete a Project

                            CrudFunctions.deleteProject(scanner,projectArrayList,statement,listIterator,connection);
                            break;

                        case 8:
                            //Finalize - Display Invoice
                            CrudFunctions.finalizeDisplayInvoice(scanner,projectArrayList,statement,listIterator,connection,results);
                            break;

                        case 9:
                            //View Completed Projects - Number & Names only
                            CrudFunctions.viewCompletedProjects(projectArrayList,statement,listIterator);
                            break;

                        case 10:
                            //View Completed Projects with details
                            CrudFunctions.viewCompletedProjectsDetails(projectArrayList,statement,listIterator);
                            break;

                        case 0:
                            //This option exits the program
                            scanner.close();
                            System.exit(0);
                            System.out.println("Program is closed");

                        default:
                            // Should a user enter an incorrect option, the following message is displayed
                            // allowing user to make a correct chose
                            System.out.println("This is not a valid option \nPlease choose another ");
                    }
                }catch (InputMismatchException e){
                    System.out.println("Invalid entry , Please try again ");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}