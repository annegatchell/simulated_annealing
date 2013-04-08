package src.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Assert;
import java.lang.Exception;
import java.util.Arrays;

import src.main.Lattice;

public class LatticeTest {
    Lattice a;
    /**
     * Sets up the test fixture. 
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        

    }

    /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        
    }


    @Test
    public void testRandomLook(){
        a = new Lattice(5, "Random", false);
        System.out.println(a);
        //a.closeFrame();
    }

    @Test 
    public void testpercentageSame(){
        int[][] input = {{1, -1, 1},
                        {1, -1, -1},
                        {-1, 1, -1}};
        a = new Lattice(input, "%", false);
        System.out.println(a.percentageSame());
        assertEquals(0.556, a.percentageSame(),0.1);
        //a.closeFrame();
    }

    @Test
    public void testNegativeLatticeSize(){
        Lattice b = new Lattice(0, "Negative size", false);
        //b.closeFrame();
    }
    
    @Test
    public void testScore() {
        int[][] input = {{1, -1, 1},
                        {1, -1, -1},
                        {-1, 1, -1}};
        a = new Lattice(input, "score", false);
        assertEquals(12, a.score());
        //a.closeFrame();
    }

    @Test
    public void testFlip(){
        int[][] input = {{1, -1, 1},
                        {1, -1, -1},
                        {-1, 1, -1}};
        a = new Lattice(input, "flip", false);
        assertEquals(12, a.score());
        assertEquals(16, a.flip(0,1));
        assertEquals(12, a.flip(0,1));
        assertEquals(16, a.flip(0,1));
       // a.closeFrame();
    }

    // @Test
    // public void testE(){
    //     assertEquals(0,a.E());
    //     a.addEdge(0,1);
    //     a.addEdge(1,2);
    //     a.addEdge(2,3);
    //     a.addEdge(3,4);
    //     a.addEdge(4,0);
    //     assertEquals(5, a.E());
    // }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main("src.test.ElementTest");
    }

}