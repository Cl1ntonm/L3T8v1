/**
 * class that holds String SQL query's to be accessed as needed
 */
public class SqlQuerieMethods {
    /**
     * Sql query for all fields in all tables for projects that are completed
     *
      * @return - Sql query in String form
     */
    public static String getSqlQueryAllCompleted() {
        String SqlQueryAllCompleted = "SELECT * \n" +
                "FROM project \n" +
                "join customer \n" +
                "on project.project_number = customer.project_number \n" +
                "join architect \n" +
                "on project.project_number = architect.project_number \n" +
                "join contractor \n" +
                "on project.project_number = contractor.project_number \n" +
                "where project.finalised = 'yes' ;";
        return SqlQueryAllCompleted;
    }

    /**
     * Sql query for all fields in all tables for a project number of the users choice
     *
     * @param projectToSearch - project number that the user chooses
     * @return - Sql query in String form
     */
    public static String getSqlQuerySearch(int projectToSearch) {

        String sqlQuerySearch = "SELECT * \n" +
                "FROM project \n" +
                "join customer \n" +
                "on project.project_number = customer.project_number \n" +
                "join architect \n" +
                "on project.project_number = architect.project_number \n" +
                "join contractor \n" +
                "on project.project_number = contractor.project_number \n" +
                "where project.project_number = "+projectToSearch;
        return sqlQuerySearch;
    }

    /**
     * Sql query for all fields in all tables for projects that on going
     *
     * @return - Sql query in String form
     */
    public static String getSqlQueryAllTablesOngoing() {
        //Sql query that joins all tables to be extracted
        String SqlQueryAllTablesOnging = "SELECT * \n" +
                "FROM project\n" +
                "join customer\n" +
                "on project.project_number = customer.project_number\n" +
                "join architect\n" +
                "on project.project_number = architect.project_number\n" +
                "join contractor\n" +
                "on project.project_number = contractor.project_number  " +
                "where project.deadline_date = project.completed_date;";

        return SqlQueryAllTablesOnging;
    }

    /**
     * Sql query for all fields in all tables for projects that at past due date
     *
     * @return - Sql query in String form
     */
    public static String getSqlQueryAllTablesPastDue() {
        //Sql query that joins all tables to be extracted
        String SqlQueryAllTablesPastDue = "SELECT * \n" +
                "FROM project\n" +
                "join customer\n" +
                "on project.project_number = customer.project_number\n" +
                "join architect\n" +
                "on project.project_number = architect.project_number\n" +
                "join contractor\n" +
                "on project.project_number = contractor.project_number \n" +
                "where project.deadline_date = project.completed_date ;";
        return SqlQueryAllTablesPastDue;
    }

    /**
     * Sql query for all fields in all tables
     *
     * @return - Sql query in String form
     */
    public static String getSqlQueryAllTables() {
        //Sql query that joins all tables to be extracted
        String SqlQueryAllTables = "SELECT * \n" +
                "FROM project\n" +
                "join customer\n" +
                "on project.project_number = customer.project_number\n" +
                "join architect\n" +
                "on project.project_number = architect.project_number\n" +
                "join contractor\n" +
                "on project.project_number = contractor.project_number;";

        return SqlQueryAllTables;
    }

    /**
     * Sql query for all fields in all tables for project number to be updated
     *
     * @param projectNumberToQuery - project number that the user chooses
     * @return - Sql query in String form
     */
    public static String getSqlQueryAllTablesOngoingUpdate(int projectNumberToQuery){
        String sqlQueryAllTablesOngoingUpdate = "SELECT * \n" +
                "FROM project \n" +
                "join customer \n" +
                "on project.project_number = customer.project_number \n" +
                "join architect \n" +
                "on project.project_number = architect.project_number \n" +
                "join contractor \n" +
                "on project.project_number = contractor.project_number \n" +
                "where project.deadline_date = project.completed_date \n" +
                "and project.project_number = "+projectNumberToQuery;
        return sqlQueryAllTablesOngoingUpdate;
    }

}
