import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Class of CRUD functions used by the Poised Program
 */
public class CrudFunctions {
    /**
     * Methods - case option 1 - Add new projects Details
     *
     * @param scanner - to accept user entered data
     * @param results - holds the row of queried data
     * @param statement - executes the query and returns the data to results variable
     * @param projectNumberList - Array list holding the list of projectas objects
     * @param connection - connection session to the database
     * @throws SQLException
     */

    public static void addNewProjectDetails(Scanner scanner, ResultSet results, Statement statement, ArrayList projectNumberList, Connection connection) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("Add New Project details");
        System.out.println("___________________________________________________________");

        //saves all the project numbers to an array , to check for duplicates
        results = statement.executeQuery("select project.project_number from project;");
        while (results.next()) {
            int projectNumber = results.getInt("project.project_number");
            projectNumberList.add(projectNumber);
        }

        // call method to accept user entered project number and ensure no duplicates
        int projectNumber = PoisedUtilities.checkDuplicate(projectNumberList, scanner);

        System.out.println("Please enter a Project name ," +
                " \nShould you rather prefer an Auto name Press 'x':");
        String projectName = scanner.nextLine();

        System.out.println("Please Enter The Project Type :");
        String projectType = scanner.nextLine();

        System.out.println("Please Enter the Project Address");
        String projectAddress = scanner.nextLine();

        System.out.println("Please Enter the Project ERF Number ");
        String projectErf = scanner.nextLine();

        System.out.println("Please Enter the Project Total Cost ");
        double projectAmountCharged = PoisedUtilities.assignAmount();

        System.out.println("Please Enter the amount Paid to date");
        double projectAmountPaid = PoisedUtilities.assignAmount();

        System.out.println("Deadline Date Input");
        LocalDate projectDeadlineDate = PoisedUtilities.assignDate();
        //assigning a date value to ensure  no null value exception errors
        LocalDate projectCompletedDate = projectDeadlineDate;

        //Accept project finalized status
        String projectFinalised = "no";

        //Accepting Customer details
        System.out.println("Please enter Customer First name ");
        String customerFirstName = scanner.nextLine();
        System.out.println("Please enter Customer Last name ");
        String customerLastName = scanner.nextLine();
        System.out.println("Please enter Customer Telephone Number");
        String customerTelephone = scanner.nextLine();
        System.out.println("Please enter Customer Email Address");
        String customerEmail = scanner.nextLine();
        System.out.println("Please enter Customer Address");
        String customerAddress = scanner.nextLine();

        //Accepting Architect details
        System.out.println("Please enter Architect First name ");
        String architectFirstName = scanner.nextLine();
        System.out.println("Please enter Architect Last name ");
        String architectLastName = scanner.nextLine();
        System.out.println("Please enter Architect Telephone Number");
        String architectTelephone = scanner.nextLine();
        System.out.println("Please enter Architect Email Address");
        String architectEmail = scanner.nextLine();
        System.out.println("Please enter Architect Address");
        String architectAddress = scanner.nextLine();

        //Accepting Contractor details
        System.out.println("Please enter Contractor First name ");
        String contractorFirstName = scanner.nextLine();
        System.out.println("Please enter Contractor Last name ");
        String contractorLastName = scanner.nextLine();
        System.out.println("Please enter Contractor Telephone Number");
        String contractorTelephone = scanner.nextLine();
        System.out.println("Please enter Contractor Email Address");
        String contractorEmail = scanner.nextLine();
        System.out.println("Please enter Contractor Address");
        String contractorAddress = scanner.nextLine();

        // Auto generate project name, as per user request , the type and surname are
        // joined and assigned to from the auto project name
        if (projectName.equals("x")) {
            projectName = projectType + " " + customerFirstName;
        }

        //creating person objects
        Person customer = new Person(projectNumber, projectNumber, customerFirstName, customerLastName,
                customerTelephone, customerEmail, customerAddress);
        Person architect = new Person(projectNumber, projectNumber, architectFirstName, architectLastName,
                architectFirstName, architectEmail, architectAddress);
        Person contractor = new Person(projectNumber, projectNumber, contractorFirstName,
                contractorLastName, contractorTelephone, contractorEmail, contractorAddress);

        //creating project object
        Project projectDetails = new Project(projectNumber, projectName, projectType, projectAddress,
                projectErf, projectAmountCharged, projectAmountPaid, projectDeadlineDate, projectFinalised,
                projectCompletedDate, customer, architect, contractor);

        //calling class method to write the object variables to the sql tables
        System.out.println(projectDetails.toSql(connection));
        System.out.println(customer.toSql(connection, "customer"));
        System.out.println(architect.toSql(connection, "architect"));
        System.out.println(contractor.toSql(connection, "contractor"));
    }

    /**
     * Methods - case option 2 - View ongoing Projects with detail
     *
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public static void viewOngoingProjectsWithDetails(ArrayList projectArrayList, Statement statement, ListIterator listIterator) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("View Ongoing Projects - with Details");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from all tables
        String SqlQueryAllTables = SqlQuerieMethods.getSqlQueryAllTablesOngoing();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQueryAllTables);

        //iterating thru each project in Array list and display Full details to the screen
        listIterator = projectArrayList.listIterator();
        while (listIterator.hasNext())
            System.out.println(listIterator.next() + " ");
    }

    /**
     * Methods - case option 3 - View Ongoing Projects, condensed view Numbers and Names only
     *
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public  static void viewOngoingProjectsNumAndNameOnly(ArrayList projectArrayList,Statement statement,ListIterator listIterator) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("View Ongoing Projects - Number & Names only");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from ongoing tables
        String SqlQueryAllTables = SqlQuerieMethods.getSqlQueryAllTablesOngoing();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQueryAllTables);

        //iterating thru each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println(" ");
    }

    /**
     *
     * Methods - case option 4 - View Ongoing Projects that are passed Due Date
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public  static void viewOngoingProjectsPassedDueDate(ArrayList projectArrayList,Statement statement,ListIterator listIterator) throws SQLException {
        boolean found = false;
        System.out.println("___________________________________________________________");
        System.out.println("View Ongoing Projects Passed Due Date");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from all tables
        String SqlQuery = SqlQuerieMethods.getSqlQueryAllTablesPastDue();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQuery);

        //iterating thru each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            if (project.getProjectDeadline().isBefore(LocalDate.now())) {
                System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
                found = true;
            }
        }
        if (!found)
            System.out.println("Project Not Found");
        System.out.println("___________________________________________________________");
    }

    /**
     * Methods - case option 5 - Search for an ongoing Project by project number
     *
     * @param scanner - to accept user entered data
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public  static void searchForOnGoingProject(Scanner scanner,ArrayList projectArrayList,Statement statement,ListIterator listIterator) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("Select Project to get full details ");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from all tables
        String getSqlQueryAllTables = SqlQuerieMethods.getSqlQueryAllTables();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, getSqlQueryAllTables);

        //iterating through each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println(" ");


        //Checking and validating input
        int projectNumberToQuery = 0;
        try {
            System.out.println("please enter project number or any key to exit search ");
            projectNumberToQuery = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..");
        }

        //calling Sql query that calls fields from requested project
        String sqlQuerySearch = SqlQuerieMethods.getSqlQuerySearch(projectNumberToQuery);
        boolean found = false;

        //calling the query and extracting to array list , to check user input is valid
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, sqlQuerySearch);

        //checking the user input against the project array list and display the correct Project
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println(project);
            found = true;
        }

        //output the appropriate display should the input be invalid
        System.out.println(" ");
        if (found == false) {
            System.out.println("Project not found");
            System.out.println("___________________________________________________________");
        }
    }

    /**
     * Methods - case option 6 - Update selected ongoing Project details
     *
     * @param scanner - to accept user entered data
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @param connection - connection session to the database
     * @param results - holds the row of queried data
     * @throws SQLException
     */
    public  static void updateOngoingProjectDetails(Scanner scanner, ArrayList projectArrayList, Statement statement, ListIterator listIterator, Connection connection, ResultSet results) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("Update Project Details");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from ongoing tables
        String getSqlQueryAllTablesOngoing = SqlQuerieMethods.getSqlQueryAllTablesOngoing();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, getSqlQueryAllTablesOngoing);

        //iterating through each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println("");

        //Checking and validating input
        int projectNumberToQuery = 0;
        try {
            System.out.println("please enter project number or 'X' Exit");
            projectNumberToQuery = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..");
        }

        //calling Sql query that calls fields from ongoing project only to update
        String sqlQuerySearch = SqlQuerieMethods.getSqlQueryAllTablesOngoingUpdate(projectNumberToQuery);
        boolean found = false;

        //calling the query and extracting to array list , to check user input is valid
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, sqlQuerySearch);

        //checking the user input against the project array list and display the correct Project
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println(project);
            found = true;
        }

        //output the appropriate display should the input be invalid
        System.out.println("");
        if (found == false) {
            System.out.println("Project not found");
            System.out.println("___________________________________________________________");
        }

        //calling Sql query that calls fields from requested project, and saving to results
        sqlQuerySearch = SqlQuerieMethods.getSqlQuerySearch(projectNumberToQuery);
        statement = connection.createStatement();
        results = statement.executeQuery(sqlQuerySearch);


        if (results.next()) {
            // fields from Project table
            String projectName = results.getString("project.name");
            String projectType = results.getString("project.type");
            String projectAddress = results.getString("project.address");
            String projectErf = results.getString("project.erf");
            float projectAmountCharged = results.getFloat("project.amount_charged");
            float projectAmountPaid = results.getFloat("project.amount_paid");
            Date projectDeadlineDateSQL = results.getDate("project.deadline_date");

            // fields from Customer table
            String customerFirstName = results.getString("customer.first_name");
            String customerLastName = results.getString("customer.last_name");
            String customerTelephone = results.getString("customer.telephone");
            String customerEmail = results.getString("customer.email");
            String customerAddress = results.getString("customer.address");

            // fields from Architect table
            String architectFirstName = results.getString("architect.first_name");
            String architectLastName = results.getString("architect.last_name");
            String architectTelephone = results.getString("architect.telephone");
            String architectEmail = results.getString("architect.email");
            String architectAddress = results.getString("architect.address");

            // fields from contractor table
            String contractorFirstName = results.getString("contractor.first_name");
            String contractorLastName = results.getString("contractor.last_name");
            String contractorTelephone = results.getString("contractor.telephone");
            String contractorEmail = results.getString("contractor.email");
            String contractorAddress = results.getString("contractor.address");

            System.out.println("What detail would you like to Update or '0' to exit");
            System.out.println("\n1 . Project Name: " + projectName);
            System.out.println("2 . Project Type: " + projectType);
            System.out.println("3 . Project Address: " + projectAddress);
            System.out.println("4 . Project ERF number: " + projectErf);
            System.out.println("5 . Project Cost: " + projectAmountCharged);
            System.out.println("6 . Project Paid to Date: " + projectAmountPaid);
            System.out.println("7 . Project Deadline Date: " + projectDeadlineDateSQL);
            System.out.println(" ");
            System.out.println("8 . Customer First Name: " + customerFirstName);
            System.out.println("9 . Customer Last Name: " + customerLastName);
            System.out.println("10. Customer Telephone: " + customerTelephone);
            System.out.println("11. Customer Email: " + customerEmail);
            System.out.println("12. Customer Address: " + customerAddress);
            System.out.println(" ");
            System.out.println("13. Architect First Name: " + architectFirstName);
            System.out.println("14. Architect Last Name: " + architectLastName);
            System.out.println("15. Architect Telephone: " + architectTelephone);
            System.out.println("16. Architect Email: " + architectEmail);
            System.out.println("17. Architect Address: " + architectAddress);
            System.out.println(" ");
            System.out.println("18. Contractor First Name: " + contractorFirstName);
            System.out.println("19. Contractor Last Name: " + contractorLastName);
            System.out.println("20. Contractor Telephone: " + contractorTelephone);
            System.out.println("21. Contractor Email: " + contractorEmail);
            System.out.println("22. Contractor Address: " + contractorAddress);
        }
        boolean endUpdate = false;
        System.out.println("\nPlease choose the number of the detail you would like to change ");

        //set tup 1st part of String Sql query
        String sqlQueryProjectUpdate = "update project set ";
        String sqlQueryCustomerUpdate = "update customer set ";
        String sqlQueryArchitectUpdate = "update architect set ";
        String sqlQueryContractorUpdate = "update contractor set ";

        // case menu structure to all user to edit a spesific field
        try {
            switch (scanner.nextInt()) {
                case 1:
                    //Change Project name

                    //declare base variables
                    PreparedStatement preparedStatement;
                    int rows;
                    System.out.println("Enter new Name:");
                    String newProjectName = scanner.next();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.name = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setString(1, newProjectName);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 2:
                    //Change Project Type
                    System.out.println("Enter new Type:");
                    String newProjectType = scanner.next();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.type = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setString(1, newProjectType);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 3:
                    //Change Project Address
                    System.out.println("Enter new Address:");
                    String newProjectAddress = scanner.next();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.address = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setString(1, newProjectAddress);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 4:
                    //Change Project ERF number
                    System.out.println("Enter new ERF number:");
                    String newProjectErf = scanner.next();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.erf = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setString(1, newProjectErf);
                    // display sucess
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 5:
                    //Change Project Cost Amount
                    System.out.println("Enter new Cost Amount:");
                    float newProjectAmountCharged = (float) PoisedUtilities.assignAmount();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.amount_charged = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setFloat(1, newProjectAmountCharged);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 6:
                    //Change Project Paid Amount
                    System.out.println("Enter new Amount Paid:");
                    float newProjectAmountPaid = (float) PoisedUtilities.assignAmount();
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.amount_paid = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setFloat(1, newProjectAmountPaid);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 7:
                    //Change Project Deadline date
                    System.out.println("Enter new Deadline Date:");
                    LocalDate newProjectDeadlineDateLocal = PoisedUtilities.assignDate();
                    Date newProjectDeadlineDateSql = Date.valueOf(newProjectDeadlineDateLocal);
                    sqlQueryProjectUpdate = sqlQueryProjectUpdate + "project.deadline_date = ? where project.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryProjectUpdate);
                    preparedStatement.setDate(1, newProjectDeadlineDateSql);
                    rows = preparedStatement.executeUpdate();
                    //display success
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }

                    // setting the completed date to be the same deadline date , to function as a null vlaue replacmnet
                    String sqlQueryProjectUpdateCompletedDate = "update project set project.completed_date = ? where project.project_number = " + projectNumberToQuery;
                    PreparedStatement preparedStatementCompletedDate = connection.prepareStatement(sqlQueryProjectUpdateCompletedDate);
                    preparedStatementCompletedDate.setDate(1, newProjectDeadlineDateSql);
                    preparedStatementCompletedDate.executeUpdate();
                    break;
                //Entering customer details
                case 8:
                    //Change Customer First name
                    System.out.println("Enter new Customer First Name:");
                    String newCustomerFirstName = scanner.next();
                    sqlQueryCustomerUpdate = sqlQueryCustomerUpdate + "customer.first_name = ? where customer.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryCustomerUpdate);
                    preparedStatement.setString(1, newCustomerFirstName);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 9:
                    //Change Customer Last name
                    System.out.println("Enter new Customer Last Name:");
                    String newCustomerLastName = scanner.next();
                    sqlQueryCustomerUpdate = sqlQueryCustomerUpdate + "customer.last_name = ? where customer.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryCustomerUpdate);
                    preparedStatement.setString(1, newCustomerLastName);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 10:
                    //Change Customer Telephone number
                    System.out.println("Enter new Customer Telephone no.:");
                    String newCustomerTelephone = scanner.next();
                    sqlQueryCustomerUpdate = sqlQueryCustomerUpdate + "customer.telephone = ? where customer.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryCustomerUpdate);
                    preparedStatement.setString(1, newCustomerTelephone);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 11:
                    //Change Customer email
                    System.out.println("Enter new Customer Email:");
                    String newCustomerEmail = scanner.next();
                    sqlQueryCustomerUpdate = sqlQueryCustomerUpdate + "customer.email = ? where customer.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryCustomerUpdate);
                    preparedStatement.setString(1, newCustomerEmail);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 12:
                    //Change Customer address
                    System.out.println("Enter new Customer Address:");
                    String newCustomerAddress = scanner.next();
                    sqlQueryCustomerUpdate = sqlQueryCustomerUpdate + "customer.address = ? where customer.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryCustomerUpdate);
                    preparedStatement.setString(1, newCustomerAddress);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                // Change Architect details
                case 13:
                    //Change Architect First name
                    System.out.println("Enter new Architect First Name:");
                    String newArchitectFirstName = scanner.next();
                    sqlQueryArchitectUpdate = sqlQueryArchitectUpdate + "architect.first_name = ? where architect.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryArchitectUpdate);
                    preparedStatement.setString(1, newArchitectFirstName);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 14:
                    //Change Architect Last Name
                    System.out.println("Enter new Architect Last Name:");
                    String newArchitectLastName = scanner.next();
                    sqlQueryArchitectUpdate = sqlQueryArchitectUpdate + "architect.last_name = ? where architect.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryArchitectUpdate);
                    preparedStatement.setString(1, newArchitectLastName);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 15:
                    //Change Architect telephone no.
                    System.out.println("Enter new Architect Telephone no.:");
                    String newArchitectTelephone = scanner.next();
                    sqlQueryArchitectUpdate = sqlQueryArchitectUpdate + "architect.telephone = ? where architect.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryArchitectUpdate);
                    preparedStatement.setString(1, newArchitectTelephone);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 16:
                    //Change Architect email
                    System.out.println("Enter new Architect Email:");
                    String newArchitectEmail = scanner.next();
                    sqlQueryArchitectUpdate = sqlQueryArchitectUpdate + "architect.email = ? where architect.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryArchitectUpdate);
                    preparedStatement.setString(1, newArchitectEmail);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 17:
                    //Change Architect Address
                    System.out.println("Enter new Architect Address:");
                    String newArchitectAddress = scanner.next();
                    sqlQueryArchitectUpdate = sqlQueryArchitectUpdate + "architect.address = ? where architect.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryArchitectUpdate);
                    preparedStatement.setString(1, newArchitectAddress);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;

                //Change Contractor details
                case 18:
                    //Change Contractor First Name
                    System.out.println("Enter new Contractor First Name:");
                    String newContractorFirstName = scanner.next();
                    sqlQueryContractorUpdate = sqlQueryContractorUpdate + "contractor.first_name = ? where contractor.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryContractorUpdate);
                    preparedStatement.setString(1, newContractorFirstName);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 19:
                    //Change Contractor Last Name
                    System.out.println("Enter new Contractor Last Name:");
                    String newContractorLastName = scanner.next();
                    sqlQueryContractorUpdate = sqlQueryContractorUpdate + "contractor.last_name = ? where contractor.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryContractorUpdate);
                    preparedStatement.setString(1, newContractorLastName);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 20:
                    //Change Contractor telephone no
                    System.out.println("Enter new Contractor Telephone no.:");
                    String newContractorTelephone = scanner.next();
                    sqlQueryContractorUpdate = sqlQueryContractorUpdate + "contractor.telephone = ? where contractor.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryContractorUpdate);
                    preparedStatement.setString(1, newContractorTelephone);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 21:
                    //Change Contractor email
                    System.out.println("Enter new Contractor Email:");
                    String newContractorEmail = scanner.next();
                    sqlQueryContractorUpdate = sqlQueryContractorUpdate + "contractor.email = ? where contractor.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryContractorUpdate);
                    preparedStatement.setString(1, newContractorEmail);
                    // display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 22:
                    //Change Contractor address
                    System.out.println("Enter new Contractor Address:");
                    String newContractorAddress = scanner.next();
                    sqlQueryContractorUpdate = sqlQueryContractorUpdate + "contractor.address = ? where contractor.project_number = " + projectNumberToQuery;
                    preparedStatement = connection.prepareStatement(sqlQueryContractorUpdate);
                    preparedStatement.setString(1, newContractorAddress);
                    //display success
                    rows = preparedStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("\nRecord updated successfully\n");
                    }
                    break;
                case 0:
                    break;
                default:
                    // Should a user enter an incorrect option, the following message is displayed
                    // allowing user to make a correct chose
                    System.out.println("This is not a valid option \nPlease choose another ");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
        }

    }

    /**
     * Methods - case option 7 - Delete selected Project from ongoing list
     *
     * @param scanner - to accept user entered data
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @param connection - connection session to the database
     * @throws SQLException
     */
    public static  void deleteProject(Scanner scanner,ArrayList projectArrayList,Statement statement,ListIterator listIterator,Connection connection) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("Delete a Project");
        System.out.println("___________________________________________________________");

        // declare base variables
        PreparedStatement preparedStatementDelete;
        int rows;

        //calling Sql query that call all fields from all tables
        String getSqlQueryAllTables = SqlQuerieMethods.getSqlQueryAllTables();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, getSqlQueryAllTables);

        //iterating through each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println(" ");

        //Checking and validating input
        int projectNumberToQuery = 0;
        try {
            System.out.println("please enter project number or 'X' Exit");
            projectNumberToQuery = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..");
        }

        //calling Sql query that calls fields from requested project
        String sqlQuerySearch = SqlQuerieMethods.getSqlQuerySearch(projectNumberToQuery);
        boolean found = false;

        //calling the query and extracting to array list , to check user input is valid
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, sqlQuerySearch);

        //checking the user input against the project array list and display the correct Project
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println(project);
            found = true;
        }

        //output the appropriate display should the input be invalid
        System.out.println("");
        if (found == false) {
            System.out.println("Project not found");
            System.out.println("___________________________________________________________");

        }

        //set tup 1st part of String Sql quer
        String sqlQueryProjectDelete = "delete from project where ";
        String sqlQueryCustomerDelete = "delete from customer where ";
        String sqlQueryArchitectDelete = "delete from architect where ";
        String sqlQueryContractorDelete = "delete from contractor where ";

        //setting uo connection to the database
        statement = connection.createStatement();

        //deleting details from the project table
        sqlQueryProjectDelete = sqlQueryProjectDelete + "project.project_number = ? ";
        preparedStatementDelete = connection.prepareStatement(sqlQueryProjectDelete);
        preparedStatementDelete.setInt(1, projectNumberToQuery);
        rows = preparedStatementDelete.executeUpdate();

        //deleting details from the customer table
        sqlQueryCustomerDelete = sqlQueryCustomerDelete + "customer.cust_id = ? ";
        preparedStatementDelete = connection.prepareStatement(sqlQueryCustomerDelete);
        preparedStatementDelete.setInt(1, projectNumberToQuery);
        rows = preparedStatementDelete.executeUpdate();

        //deleting details from the architect table
        sqlQueryArchitectDelete = sqlQueryArchitectDelete + "architect.arch_id = ? ";
        preparedStatementDelete = connection.prepareStatement(sqlQueryArchitectDelete);
        preparedStatementDelete.setInt(1, projectNumberToQuery);
        rows = preparedStatementDelete.executeUpdate();

        //deleting details from the contractor table
        sqlQueryContractorDelete = sqlQueryContractorDelete + "contractor.con_id = ? ";
        preparedStatementDelete = connection.prepareStatement(sqlQueryContractorDelete);
        preparedStatementDelete.setInt(1, projectNumberToQuery);
        rows = preparedStatementDelete.executeUpdate();

        //display success
        if (rows > 0) {
            System.out.println("Record is deleted successfully");
        }
    }

    /**
     * Methods - case option 8 - Mark Project as Finalized and move Project to Completed file
     *
     * @param scanner - to accept user entered data
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @param connection - connection session to the database
     * @param results - holds the row of queried data
     * @throws SQLException
     */
    public static  void finalizeDisplayInvoice(Scanner scanner, ArrayList projectArrayList,Statement statement,ListIterator listIterator,Connection connection, ResultSet results) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("Finalize - Display Invoice");
        System.out.println("___________________________________________________________");

        //calling Sql query that call all fields from ongoing projects in all tables
        String SqlQueryAllTablesOngoing = SqlQuerieMethods.getSqlQueryAllTablesOngoing();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQueryAllTablesOngoing);

        //iterating through each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println(" ");

        //Checking and validating input
        int projectNumberToQuery = 0;
        try {
            System.out.println("please enter project number or 'X' Exit");
            projectNumberToQuery = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input..");
        }

        //calling Sql query that calls fields from requested project
        String sqlQuerySearch = SqlQuerieMethods.getSqlQuerySearch(projectNumberToQuery);
        boolean found = false;

        //calling the query and extracting to array list , to check user input is valid
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, sqlQuerySearch);

        //checking the user input against the project array list and display the correct Project
        listIterator = projectArrayList.listIterator();
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println(project);
            found = true;
        }

        //output the appropriate display should the input be invalid
        System.out.println("");
        if (found == false) {
            System.out.println("Project not found");
            System.out.println("___________________________________________________________");
        }

        //accepting completed date and converting it pt the sql format
        System.out.println("Enter the Completed date");
        LocalDate newCompletedDateLocal = PoisedUtilities.assignDate();
        Date newCompletedDateSql = Date.valueOf(newCompletedDateLocal);

        //calling Sql query that calls fields from requested project
        String sqlQueryToUpdate = SqlQuerieMethods.getSqlQuerySearch(projectNumberToQuery);
        statement = connection.createStatement();
        results = statement.executeQuery(sqlQueryToUpdate);

        if (results.next()) {
            //declare base basiable
            PreparedStatement preparedStatement;

            //updating status as finalised
            String sqlQueryChangeFinalized = "update project set project.finalised = ? where project.project_number = " + projectNumberToQuery;
            preparedStatement = connection.prepareStatement(sqlQueryChangeFinalized);
            preparedStatement.setString(1, "yes");
            preparedStatement.execute();

            //updating the completed date to a project
            String sqlQueryAddCompletedDate = "update project set project.completed_date = ? where project.project_number = " + projectNumberToQuery;
            preparedStatement = connection.prepareStatement(sqlQueryAddCompletedDate);
            preparedStatement.setDate(1, newCompletedDateSql);
            preparedStatement.executeUpdate();

            // retrieving fields from Project table for display
            float projectAmountCharged = results.getFloat("project.amount_charged");
            float projectAmountPaid = results.getFloat("project.amount_paid");

            // retrieving fields from Customer table for display
            String customerFirstName = results.getString("customer.first_name");
            String customerLastName = results.getString("customer.last_name");
            String customerTelephone = results.getString("customer.telephone");
            String customerEmail = results.getString("customer.email");
            String customerAddress = results.getString("customer.address");

            //displaying the Invoive for the customer
            double remainingAmount = 0;
            if (projectAmountCharged > projectAmountPaid) {
                remainingAmount = projectAmountCharged - projectAmountPaid;
            }
            System.out.println("======================================================");
            System.out.println("CLIENT INVOICE ");
            System.out.println("Customer Info");
            System.out.println(customerFirstName + " " + customerLastName);
            System.out.println(customerTelephone);
            System.out.println(customerEmail);
            System.out.println(customerAddress);
            System.out.println("\nAmount Due: " + remainingAmount);
            System.out.println("======================================================");
        }
    }

    /**
     * Methods - case option 9 - View Completed Projects, condensed view Numbers and Names only
     *
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public static void viewCompletedProjects(ArrayList projectArrayList,Statement statement,ListIterator listIterator) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("View Completed Projects - Number & Names only");
        System.out.println("___________________________________________________________");

        //calling Sql query that calls field from completed projects
        String SqlQueryAllCompleted = SqlQuerieMethods.getSqlQueryAllCompleted();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQueryAllCompleted);

        //iterating through each project in Array list and display partial info to the screen
        listIterator = projectArrayList.listIterator();
        System.out.println("___________________________________________________________");
        while (listIterator.hasNext()) {
            Project project = (Project) listIterator.next();
            System.out.println("Project Number: " + project.projectNumber + " | Project Name: " + project.getProjectName());
        }
        System.out.println(" ");
    }

    /**
     * Methods - case option 10 - View Completed Projects with full details
     *
     * @param projectArrayList - Array list holding the list of project objects
     * @param statement - executes the query and returns the data to results variable
     * @param listIterator - to run through the objects in the arraylist
     * @throws SQLException
     */
    public  static void viewCompletedProjectsDetails(ArrayList projectArrayList,Statement statement, ListIterator listIterator) throws SQLException {
        System.out.println("___________________________________________________________");
        System.out.println("View Completed Projects with details");
        System.out.println("___________________________________________________________");

        //calling Sql query that calls field from completed projects
        String SqlQueryAllCompleted = SqlQuerieMethods.getSqlQueryAllCompleted();

        //calling method to extract the fields from Sql tables to an Array list for display
        projectArrayList.clear();
        projectArrayList = PoisedUtilities.sqlFieldExtraction(statement, projectArrayList, SqlQueryAllCompleted);
        listIterator = projectArrayList.listIterator();
        while (listIterator.hasNext())
            System.out.println(listIterator.next() + " ");
    }

}