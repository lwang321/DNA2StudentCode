import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DNA 2.0
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA.
 * </p>
 * <p>
 * To use this test file, run either the entire thing or individual tests (one at a time).
 * Each test will load data from [test number].txt, which is in the test_files directory.
 * </p>
 */

public class DNATest {

    private final DNA studentSolution = new DNA();
    private String sequence;
    private String STR;

    @Test
    @Timeout(value = 80, unit = TimeUnit.MILLISECONDS)
    public void testBasic() {
        setTestData(0);
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.MILLISECONDS)
    public void testMore() {
        setTestData(5);
    }

    @Test
    @Timeout(value = 40, unit = TimeUnit.MILLISECONDS)
    public void testEvenMore() {
        setTestData(19);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void testTricky() {
        setTestData(1);
    }

    @Test
    @Timeout(value = 20000, unit = TimeUnit.MILLISECONDS)
    public void testLargest() {
        int maxSize = 100000000;
        char STR = 'A';
        try {
            char[] largeCharArray = new char[maxSize];
            Arrays.fill(largeCharArray, STR);
            String largeString = new String(largeCharArray);
            assertEquals(maxSize / 10, studentSolution.STRCount(largeString, "AAAAAAAAAA"),
                    "Test Largest failed: should return " + maxSize / 10);
        } catch (OutOfMemoryError e) {
            System.err.println("Ran out of memory trying to create a large string");
        }
    }

    @Test
    @Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
    public void testChromosome22() {

        try {
            BufferedReader testReader = new BufferedReader(new FileReader("test_files/chromosome_22.txt"));
            char[] sequenceArr = new char[100000000];
            int index = 0;
            String line;
            while ((line = testReader.readLine()) != null) {
                int lineLen = line.length();
                for (int i = 0; i < lineLen; i++) {
                    sequenceArr[index++] = Character.toUpperCase(line.charAt(i));
                }
            }
            int expected = 9;
            String sequence = new String(sequenceArr);
            assertEquals(expected, studentSolution.STRCount(sequence, "AAAAC"),
                    "Test Largest failed: should return " + expected);
        } catch (IOException e) {
            System.err.println("Ran out of memory trying to create a large string");
        }
    }

    private void setTestData(int testNumber) {
        // Open files
        try {
            BufferedReader testReader = new BufferedReader(new FileReader("test_files/" + testNumber + ".txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("test_files/" + testNumber + "_answers.txt"));

            // Get the number of tests in the file
            int numTests = Integer.parseInt(testReader.readLine());
            sequence = testReader.readLine();

            // Read in the data for each test, then run.
            for (int i = 0; i < numTests; i++)
            {
                int answer = Integer.parseInt(answerReader.readLine());
                STR = testReader.readLine();
                assertEquals(answer, studentSolution.STRCount(sequence, STR),
                        "Test " + testNumber + " failed: should return " + answer);
            }
        } catch (IOException e) {
            System.out.println("Error opening test file #" + testNumber);
            e.printStackTrace();
        }
    }
}
