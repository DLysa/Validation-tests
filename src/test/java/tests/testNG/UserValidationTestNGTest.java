package tests.testNG;

import com.example.tests.utils.JsonReader;
import org.testng.annotations.Test;

import java.util.*;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

public class UserValidationTestNGTest {

    @Test(groups = {"basic"})
    public void testJsonValidationWithTestNG() throws Exception {
        List<Map<String, Object>> users = JsonReader.readJsonArray("data/users.json");
        Set<Integer> ids = new HashSet<>();
        Pattern emailRegex = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

        for (Map<String, Object> user : users) {
            assertTrue(user.containsKey("id"));
            assertTrue(user.containsKey("email"));
            assertTrue(user.containsKey("age"));

            int id = (int) user.get("id");
            String email = (String) user.get("email");
            int age = (int) user.get("age");

            assertTrue(ids.add(id), "Duplicate ID: " + id);
            assertTrue(emailRegex.matcher(email).matches(), "Invalid email: " + email);
            assertTrue(age >= 18, "Age too low: " + age);
        }
    }

    @Test(groups = {"advanced"})
    public void testUserAgeWithTestNG() {
        int age = 25;
        assertTrue(age >= 18, "User must be adult");
    }

    @Test(groups = {"advanced"})
    public void testEmailPatternWithTestNG() {
        String email = "user@example.com";
        Pattern emailRegex = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
        assertTrue(emailRegex.matcher(email).matches());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, groups = {"advanced"})
    public void flakyTest() {
        int random = new Random().nextInt(5);
        assertTrue(random != 0, "Random test failed, retrying...");
    }
}
