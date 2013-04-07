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
        a = new Lattice(5);
        System.out.println(a);
    }

    @Test
    public void testArrayConstructor(){
        int[][] input = {{1, -1, 1},
                        {1, -1, -1},
                        {-1, 1, -1}};
        a = new Lattice(input);
        assertEquals(12, a.getScore());
    }

    @Test
    public void testNegativeLatticeSize(){
        Lattice b = new Lattice(0);
    }
    
    @Test
    public void testScore() {
        
    }

    // @Test
    // public void testFlip(){
    
    // }

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