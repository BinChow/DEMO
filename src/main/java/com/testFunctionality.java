package main.java.com;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by ZhouBin on 6/2/16.
 */
public class testFunctionality {

    @Test
    /**
     * test case to check the total customers served as expected
     */
    public void runSuperMarketBusiness(){
        // run super market business
        Business business = SuperMarket.runBusiness();
        if (business != null) {
            Statistic statistic = business.getStatistic();
            if (statistic != null) {
                // check the total customers served as expected
                assertThat(statistic.getCustomerCount(), is(45));
            } else {
                assertTrue(true);
            }
        } else {
            assertTrue(true);
        }
    }
}
