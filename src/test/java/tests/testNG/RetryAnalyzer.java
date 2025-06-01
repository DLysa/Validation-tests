package tests.testNG;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetry) {
            retryCount++;
            System.out.println("Retrying test... attempt " + (retryCount + 1));
            return true;
        }
        return false;
    }
}
