package runner;

import io.cucumber.junit.*;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/cucumber",
        glue = {"steps"},
        plugin = {"pretty"}
)
public class TestRunner {

}