package ge.tbc.testautomation.util;

import org.testng.ITestResult;
import org.testng.IRetryAnalyzer;

import static ge.tbc.testautomation.data.Constants.MAX_RETRIES;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attempt = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < MAX_RETRIES) {
            attempt++;
            System.out.println("[Retry] " + result.getName()
                    + " - attempt " + attempt + "/" + MAX_RETRIES);
            return true;
        }
        return false;
    }
}