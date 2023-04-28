import static junit.Assert.assertEquals;  
import junit.After;  
import junit.AfterClass;  
import junit.Before;  
import junit.BeforeClass;  
import junit.Test;  

public class test extends demo{
  
        @BeforeClass  
        public static void setUpBeforeClass() throws Exception {  
            System.out.println("before class");  
        }  
        @Before  
        public void setUp() throws Exception {  
            System.out.println("before");  
        }  
      
        @Test  
        public void testFindMax(){  
            System.out.println("test case find max");  
            assertEquals(4,demo.findMax(new int[]{1,3,4,2}));  
            assertEquals(-2,demo.findMax(new int[]{-12,-3,-4,-2}));  
        }  
        @Test  
        public void testCube(){  
            System.out.println("test case cube");  
            assertEquals(27,demo.cube(3));  
        }  
    
}
