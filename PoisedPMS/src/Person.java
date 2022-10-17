import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class for Creation of Person Objects
 */
public class Person {
    //Attributes
    private int personID;
    private int projectNumber;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String address;


    // Constructor
    public Person(int personID, int projectNumber, String name, String surname, String phoneNumber, String email, String address) {
        this.personID = personID;
        this.projectNumber = projectNumber;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;

    }

    //Getters
    public String getSurname() {
        return surname;
    }

    //Methods
    /**
     * Displays Information on screen
     *
     * @return Displays to screen the stored Person objects values
     */
    public String toString() {
        return "Name: " + name + " " + surname
                + "\nPhone Number: " + phoneNumber
                + "\nEmail Address: " + email
                + "\nAddress : " + address;
    }

    /**
     * Takes user entered values and passed and inserted them into the Database Table
     *
     * @param connection - connection session to the database
     * @param personType - variable used to indicate which table to write person details to
     * @return the success status when fields are written to te database tables
     * @throws SQLException
     */
    public String toSql(Connection connection, String personType) throws SQLException {

        String success = null;
        String personInsertSql = null;

        String insertCustomerTable = "insert into customer (cust_id, project_number, first_name, last_name, telephone, email, address) values(?,?,?,?,?,?,?)";
        String insertArchitectTable = "insert into architect (arch_id, project_number, first_name, last_name, telephone, email, address) values(?,?,?,?,?,?,?)";
        String insertContractorTable = "insert into contractor (con_id, project_number, first_name, last_name, telephone, email, address) values(?,?,?,?,?,?,?)";

        switch (personType){
            case "customer":
                personInsertSql = insertCustomerTable;
                break;
            case "architect":
                personInsertSql = insertArchitectTable;
                break;
            case "contractor":
                personInsertSql = insertContractorTable;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(personInsertSql);
        preparedStatement.setInt(1,personID);
        preparedStatement.setInt(2,projectNumber);
        preparedStatement.setString(3,name);
        preparedStatement.setString(4,surname);
        preparedStatement.setString(5,phoneNumber);
        preparedStatement.setString(6,email);
        preparedStatement.setString(7,address);

        int rows = preparedStatement.executeUpdate();
        if(rows > 0 ){
            success = personType+" record updated successfully";
        }
        return success;
    }
}

