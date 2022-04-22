package ntnu.idatt.boco.repository;

import ntnu.idatt.boco.model.User;
import ntnu.idatt.boco.security.Encryption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void successfullyAddedUser(){
        /*
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:src/main/resources/schema.sql")
                .addScript("classpath:Users/Kyllingvinger/Desktop/projekt sys2/backend/backend/src/main/.sql")
                .build();

        userRepository.setDataSource(dataSource);
        */
        User user = new User();
        user.setEmail("d");
        user.setFname("d");
        user.setLname("d");
        user.setPassword("d");
        assertEquals(1, userRepository.saveUserToDatabase(user));
    }
}
