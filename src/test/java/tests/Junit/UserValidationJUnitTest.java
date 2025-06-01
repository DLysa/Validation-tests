package tests.Junit;

import com.example.tests.utils.JsonReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationJUnitTest {

    static int flakyCounter = 0;

    @Test
    @Tag("basic")
    void testJsonValidationWithJUnit() throws Exception {
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

    @ParameterizedTest(name = "Check if age {0} is valid")
    @ValueSource(ints = {17, 18, 20, 25})
    @Tag("advanced")
    void testAgeValidationParam(int age) {
        assertTrue(age >= 18, "Age too low: " + age);
    }

    // Flaky test with retry using TestTemplate
    @TestTemplate
    @ExtendWith(RetryTestTemplateExtension.class)
    @Tag("advanced")
    void flakyTestWithRetry() {
        flakyCounter++;
        System.out.println("Running flakyTestWithRetry, attempt #" + flakyCounter);
        if (flakyCounter < 3) {
            fail("Random failure on purpose");
        }
    }

    // Retry extension implementation
    public static class RetryTestTemplateExtension implements TestTemplateInvocationContextProvider {

        private static final int MAX_RETRIES = 3;

        @Override
        public boolean supportsTestTemplate(ExtensionContext context) {
            return true;
        }

        @Override
        public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
            return IntStream.rangeClosed(1, MAX_RETRIES)
                    .mapToObj(RetryInvocationContext::new);
        }

        static class RetryInvocationContext implements TestTemplateInvocationContext {
            private final int attempt;

            RetryInvocationContext(int attempt) {
                this.attempt = attempt;
            }

            @Override
            public String getDisplayName(int invocationIndex) {
                return "Attempt " + attempt;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new RetryTestExecutionCondition(attempt));
            }
        }

        static class RetryTestExecutionCondition implements ExecutionCondition {
            private final int attempt;

            RetryTestExecutionCondition(int attempt) {
                this.attempt = attempt;
            }

            @Override
            public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
                System.out.println("Starting attempt #" + attempt);
                return ConditionEvaluationResult.enabled("Running attempt " + attempt);
            }
        }
    }
}
