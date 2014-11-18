import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by newScanTron on 10/23/2014.
 */
public class mainTest
{

    protected int opsTestInt;
    protected String opsTestString = "";

    public class TestJunit {
        @Test
        public void testAdd() {
            String str= "Junit is working fine";
            assertEquals("Junit is working fine",str);
        }
    }

}
