package cucumber.Options;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"stepDefinitions"},
        plugin = {"json:target/jsonReports/cucumber-report.json","pretty"},
        tags = "@DeletePlace"
)
public class TestRunner {

}
