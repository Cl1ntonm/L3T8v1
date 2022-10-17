import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class of functions used in the Poised program
 */
public class PoisedUtilities {

    /**
     * Method to display the menu options
     */
    public static void menuOption() {
        System.out.println("___________________________________________________________");
        System.out.println("WELCOME TO POISED CONSTRUCTION ");
        System.out.println("Please choose one of the following options");
        System.out.println("By Typing in one of the numbers '1' to '10' ");
        System.out.println("\n1.  Add New Project details");
        System.out.println("2.  View Ongoing Projects - Full Details");
        System.out.println("3.  View Ongoing Projects - Name and Number");
        System.out.println("4.  View Ongoing Projects Passed Due Date");
        System.out.println("5.  Search for Project - Full Details");
        System.out.println("6.  Update Ongoing Project Details");
        System.out.println("7.  Delete a Project ");
        System.out.println("8.  Finalize and Display Invoice ");
        System.out.println("9.  View Completed Projects - Name and Number");
        System.out.println("10. View Completed Projects Full Details");
        System.out.println("0. To exit\n ");
        System.out.println("Enter your menu choice: ");
    }

    /**
     * Method that ensure User enters the amounts in the correct numerical format
     * @return - the user entered amounts but only ing the correct format
     */
    public static double assignAmount(){
        //declare base variables
        boolean amountStop;
        double enteredAmount = 0;
        Scanner amountScanner;
        amountScanner = new Scanner(System.in);
        amountStop = false;

        //loops the code until the correct usable type of data is inputted
        do {
            try{
                System.out.println("Enter the Amount value in Numerical format eg 1000 or 1000.00");
                enteredAmount = Double.parseDouble(amountScanner.nextLine());
                break;
            }catch (Exception  e){
                System.out.println("Invalid entry");
            }
        }while (amountStop == false);
        return enteredAmount;

    }

    /**
     * Method that accepts the deadline/completion date in only the correct format
     * @return - the user entered date but only in the correct format
     */
    public static LocalDate assignDate() {
        //declare base variables
        boolean dateStop;
        LocalDate enteredDate;
        Scanner dateScanner;
        dateScanner = new Scanner(System.in);
        dateStop = false;
        enteredDate = null;

        //loops the code until the correct usable type of data is inputted
        do {
            try {
                System.out.println("Please Enter the date in the following format: eg 2010-12-31");

                enteredDate = LocalDate.parse(dateScanner.nextLine());

                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid entry");
            }
        } while (dateStop == false);
        return enteredDate;
    }

    /**
     * Method that checks if the user input project number already exists
     *
     * @param projectNumberList - holds list of existing project number taken from MySql
     * @param scanner - - to accept user entered data
     * @return
     */
    public static int checkDuplicate(ArrayList<Integer> projectNumberList, Scanner scanner) {
        boolean checkDuplicate = false;
        int projectNumber = 0;
        while (checkDuplicate == false) {
            System.out.println("Please Enter the Project Number");
            projectNumber = scanner.nextInt();
            scanner.nextLine();

            if (projectNumberList.contains(projectNumber)) {
                System.out.println("Project Number already exists");
                checkDuplicate = false;
            } else {
                break;
            }
        }
        return projectNumber;
    }

    /**
     * Method that queries the database and returns all project objects and stores them in an array list
     *
     * @param statement - executes the query and returns the data to results variable
     * @param projectArrayList - Array list holding the list of project objects
     * @param SqlQuery - holding variable for the various String sql query's that it may contain
     * @return - an array list all project objects
     * @throws SQLException
     */
    public static ArrayList<Project> sqlFieldExtraction(Statement statement, ArrayList<Project> projectArrayList, String SqlQuery) throws SQLException {

        //saving Sql query to results
        ResultSet results = statement.executeQuery(SqlQuery);

        projectArrayList.clear();

        // iterate thru row , extracting fields to be displayed
        while (results.next()) {
            // fields from Project table
            int projectNumber = results.getInt("project.project_number");
            String projectName = results.getString("project.name");
            String projectType = results.getString("project.type");
            String projectAddress = results.getString("project.address");
            String projectErf = results.getString("project.erf");
            float projectAmountCharged = results.getFloat("project.amount_charged");
            float projectAmountPaid = results.getFloat("project.amount_paid");
            //using pass-through function method for java and sql date incompatibility
            Date projectDeadlineDateSQL = results.getDate("project.deadline_date");
            LocalDate localDateDeadLineDate = projectDeadlineDateSQL.toLocalDate();
            //using pass-through function method for java and sql date incompatibility
            Date projectCompletedDateSQL = results.getDate("project.completed_date");
            LocalDate localDateCompletedDate = projectCompletedDateSQL.toLocalDate();
            String projectFinalised = results.getString("project.finalised");

            // fields from Customer table
            int customerId = results.getInt("customer.cust_id");
            int custProjectNumber = results.getInt("customer.project_number");
            String customerFirstName = results.getString("customer.first_name");
            String customerLastName = results.getString("customer.last_name");
            String customerTelephone = results.getString("customer.telephone");
            String customerEmail = results.getString("customer.email");
            String customerAddress = results.getString("customer.address");

            // fields from Architect table
            int architectId = results.getInt("architect.arch_id");
            int architectNumber = results.getInt("architect.project_number");
            String architectFirstName = results.getString("architect.first_name");
            String architectLastName = results.getString("architect.last_name");
            String architectTelephone = results.getString("architect.telephone");
            String architectEmail = results.getString("architect.email");
            String architectAddress = results.getString("architect.address");

            // fields from contractor table
            int contractorId = results.getInt("contractor.con_id");
            int contractorNumber = results.getInt("contractor.project_number");
            String contractorFirstName = results.getString("contractor.first_name");
            String contractorLastName = results.getString("contractor.last_name");
            String contractorTelephone = results.getString("contractor.telephone");
            String contractorEmail = results.getString("contractor.email");
            String contractorAddress = results.getString("contractor.address");

            // creating Person class objects from extracted sql fields
            Person customer = new Person(customerId,custProjectNumber,customerFirstName, customerLastName,
                    customerTelephone, customerEmail, customerAddress);
            Person architect = new Person(architectId,architectNumber,architectFirstName, architectLastName,
                    architectTelephone, architectEmail, architectAddress);
            Person contractor = new Person(contractorId,contractorNumber,contractorFirstName,
                    contractorLastName, contractorTelephone, contractorEmail, contractorAddress);
            Project project = new Project(projectNumber, projectName, projectType, projectAddress,
                    projectErf, projectAmountCharged, projectAmountPaid, localDateDeadLineDate,
                    projectFinalised, localDateCompletedDate, customer, architect, contractor);

            // adding Project objects to Array list for iterating display purposes
            projectArrayList.add(project);
        }
        return projectArrayList;
    }


}
