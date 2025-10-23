package co.unicauca.gestrabajogradojpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@ActiveProfiles("h")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

class GesTrabajoGradoJpaApplicationTests {

    @Test
    void contextLoads() {
    }

}
