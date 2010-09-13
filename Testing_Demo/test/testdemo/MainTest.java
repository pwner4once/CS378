/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testdemo;

import RegTest.Diff;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dsb
 */
public class MainTest {

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testMain() {
        Diff.initSortedTest("out.txt", "correct.txt");
        testdemo.Main.main(null);
        if (Diff.finishTest()) {
            fail("Main test fails");
        }
    }

}