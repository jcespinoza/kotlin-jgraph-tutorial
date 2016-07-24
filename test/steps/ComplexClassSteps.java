package steps;

import complex.ComplexClass;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import specs.SpecContext;

public class ComplexClassSteps {
    @Given("^an integer \"([^\"]*)\" with value (\\d+)$")
    public void anIntegerWithValue(String name, int value) throws Throwable {
        SpecContext.context.put(name, value);
    }

    @When("^I calculate the sum of \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iCalculateTheSumOfAnd(String name1, String name2) throws Throwable {
        int value1 = (int)SpecContext.context.get(name1);
        int value2 = (int)SpecContext.context.get(name2);
        int result = new ComplexClass(value1, value2).getSum();

        SpecContext.context.put("result", result);
    }

    @Then("^it should return (\\d+)$")
    public void itShouldReturn(int expectedResult) throws Throwable {
        int actualResult = (int)SpecContext.context.get("result");
        assert(actualResult == expectedResult );
    }
}
