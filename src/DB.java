import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    final String URL = "jdbc:derby:sampleDB2;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    Connection conn;
    Statement createStatement;
    DatabaseMetaData dbmd;

    public DB() {

        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if (!rs.next()) {
                createStatement.execute("create table contacts(id INT not null primary key generated always as identity (start with 1, increment by 1), lastname varchar(20), firstname varchar(20), email varchar(30))");
                System.out.println("új tábla kész");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> getAllContacts() {
        String sql = "select * from contacts";
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            while (rs.next()) {
                Person actualPerson = new Person(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"), rs.getString("email"));
                users.add(actualPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addContact(Person person) {
        try {
            String sql = "insert into contacts (lastname, firstname, email) values (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Hiba a contact hozzáadással");
            e.printStackTrace();
        }
    }

    public void updateContact(Person person) {
        try {
            String sql = "update contacts set lastname = ?, firstname = ?, email = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getLastName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, Integer.parseInt(person.getId()));
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Hiba a contact hozzáadással");
            e.printStackTrace();
        }
    }
}