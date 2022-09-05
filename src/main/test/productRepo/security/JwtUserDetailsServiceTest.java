package productRepo.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class JwtUserDetailsServiceTest {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("testUser");
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), "testUser");
    }
}