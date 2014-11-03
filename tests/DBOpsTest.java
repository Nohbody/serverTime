import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBOpsTest
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

    @Before
    public void setUp() throws Exception
    {

    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void testConnect() throws Exception
    {
        //org.junit.Assert.assertEquals(true, DBOps.connect());
    }

    @Test
    public void testSearchRecipe() throws Exception
    {

    }
}