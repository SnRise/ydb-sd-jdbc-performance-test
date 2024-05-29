package tech.ydb.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author Madiyar Nurgazin
 */
@Repository("personRepositoryJdbc")
public class PersonRepository {
    @Value("${jdbcUrl}")
    String jdbcUrl;

    public List<Person> findByIdBetween(int from, int to) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.setReadOnly(true);
            String sql = "select * from persons where id between ? and ?;";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, from);
                statement.setInt(2, to);
                try (ResultSet rs = statement.executeQuery()) {
                    return extractResult(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Person> findAllWithLimit() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.setReadOnly(true);
            String sql = "select * from persons limit 100;";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    return extractResult(rs);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Person> extractResult(ResultSet rs) throws SQLException {
        List<Person> persons = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            persons.add(new Person(id, firstName, lastName));
        }

        return persons;
    }

    public Person save(Person person) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            String sql = "insert into persons (id, first_name, last_name) values (?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, person.getId());
                statement.setString(2, person.getFirstName());
                statement.setString(3, person.getLastName());

                statement.executeUpdate();

                return person;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            try (PreparedStatement statement = connection.prepareStatement("delete from persons where id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
