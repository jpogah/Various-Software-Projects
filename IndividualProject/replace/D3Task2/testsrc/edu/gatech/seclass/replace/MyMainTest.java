package edu.gatech.seclass.replace;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by unazi on 11/10/16.
 */
public class MyMainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;


    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities

    private File createTmpFile() throws IOException {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    private File createInputFile(String input) throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write(input);

        fileWriter.close();
        return file1;
    }
    private File createInputFile1() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile2() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill," + System.lineSeparator() +
                "This is another test file for the replace utility" + System.lineSeparator() +
                "that contains a list:" + System.lineSeparator() +
                "-a) Item 1" + System.lineSeparator() +
                "-b) Item 2" + System.lineSeparator() +
                "..." + System.lineSeparator() +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }

    private File createInputFile3() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?" + System.lineSeparator() +
                "It is important to know your abc and 123," +
                "so you should study it" + System.lineSeparator() +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }

    private File createInputFile4() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("");

        fileWriter.close();
        return file;
    }

    private File createInputFile5() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("First line: not replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: not replaced" + System.lineSeparator());

        fileWriter.close();
        return file;
    }

    private File createInputFile6() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("ababab");

        fileWriter.close();
        return file;
    }

    private File createInputFile7() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("The goal here is to replace string \"-i\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -i and -f");

        fileWriter.close();
        return file;
    }

    private File createInputFile8() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("Let's have some numbers in the file: 12345678");

        fileWriter.close();
        return file;
    }

    private File createInputFile9() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("-- -- -- --");

        fileWriter.close();
        return file;
    }
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }





    /*
Implementation of test frame 21
 */
    @Test
    public void myMainTest1() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say Hello unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }

    /*
Implementation of test frame 22
*/
    @Test
    public void myMainTest2() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"Howdy", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Implementation of test frame 23
*/
    @Test
    public void myMainTest3() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say Hello unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Implementation of test frame 24
*/
    @Test
    public void myMainTest4() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "Howdy", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello\" unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Implementation of test frame 25
*/
    @Test
    public void myMainTest5() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Implementation of test frame 26
*/
    @Test
    public void myMainTest6() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Howdy", "\"Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";



        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }

    /*
Implementation of test frame 27
*/
    @Test
    public void myMainTest7() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Implementation of test frame 28
*/
    @Test
    public void myMainTest8() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";


        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Howdy", "\"Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
 Implementation of test frame 38
 */
    @Test
    public void myMainTest9() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "Howdy", "\"Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"\"Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }

    /*
Implementation of test frame 42
*/
    @Test
    public void myMainTest10() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Howdy", "\"Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }

/*
Implementation of test frame 1
 */

    @Test
    public void myMainTest11() throws Exception {

        String input1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String input2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String input3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }


/*
Implementation of test frame 2
 */

    @Test
    public void myMainTest12() throws Exception {

        String input1 = "";

        File inputFile1 = createInputFile(input1);

        String args[] = {"Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

    }

    /*
Implementation of test frame 3
 */

    @Test
    public void myMainTest13() throws Exception {

        String input1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String input2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String input3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"tomorrow", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";
        String expected3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }


    /*
Implementation of test frame 4
 */

    @Test
    public void myMainTest14() throws Exception {

        String input1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...";

        String input2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...";

        String input3 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }



    /*
Implementation of test frame 5
 */

    @Test
    public void myMainTest15() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String input2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";

        String input3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Hello Bill, have you learned your abc and 123?";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }

    /*
Implementation of test frame 6
 */

    @Test
    public void myMainTest16() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String input2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";

        String input3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";
        String expected2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";
        String expected3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }



    /*
Implementation of test frame 8
 */

    @Test
    public void myMainTest18() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        File inputFile1 = createInputFile(input1);

        String args[] = {"H", "Test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Testowdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

    }


        /*
Implementation of test frame 9
 */
 //
    @Test
    public void myMainTest19() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String from = "It is important to know your abc and 123," +
                "so you should study it and then repeat with me: abc and 123" +
                "Howdy Bill, have you learned your abc and 123?" +
                "This is another test file for the replace utility" +
                "that contains a list:" +
                "-a\\) Item 1" +
                "-b\\) Item 2" +
                "..." +
                "and says \"Howdy Bill\" twice";


        File inputFile1 = createInputFile(input1);

        String args[] = {from, "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);
    }




    /*
Implementation of test frame 15
 */

    @Test
    public void myMainTest20() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        File inputFile1 = createInputFile(input1);

        String args[] = {"Howdy", "H", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"H bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The files differ!", expected1, actual1);

    }




       /*
Implementation of test frame 20
 */

    @Test
    public void myMainTest21() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String to = "It is important to know your abc and 123," +
                "so you should study it" +
                "and then repeat with me: abc and 123" +
                "Howdy Bill, have you learned your abc and 123?" +
                "This is another test file for the replace utility" +
                "that contains a list:" +
                "-a) Item 1" +
                "-b) Item 2" +
                "..." +
                "and says \"Howdy Bill\" twice";


         File inputFile1 = createInputFile(input1);
        inputFile1.delete();

        String args[] = {"Howdy", to, "--", inputFile1.getPath()};
        Main.main(args);

        assertEquals("File " + inputFile1.getName() + " not found", errStream.toString().trim());

    }





    /*
Implementation of test frame 40
 */

    @Test
    public void myMainTest22() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String input2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";

        String input3 = "Howdy Bill It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"-i", "Howdy", "\"Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"\"Hello bill\" again!";
        String expected2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"\"Hello Bill\" twice";
        String expected3 = "\"Hello Bill It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "\"Hello Bill, have you learned your abc and 123?";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }



        /*
Implementation of test frame 41
 */

    @Test
    public void myMainTest23() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String input2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";

        String input3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"-i", "\"Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say Hello bill\" again!";
        String expected2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says Hello Bill\" twice";
        String expected3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "Howdy Bill, have you learned your abc and 123?";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }


    /*
Implementation of test frame 42
 */

    @Test
    public void myMainTest24() throws Exception {

        String input1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Howdy bill\" again!";

        String input2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Howdy Bill\" twice";

        String input3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "\"Howdy Bill, have you learned your abc and 123?";

        File inputFile1 = createInputFile(input1);
        File inputFile2 = createInputFile(input2);
        File inputFile3 = createInputFile(input3);

        String args[] = {"-i", "\"Howdy", "\"Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123\n" +
                "\"Hello Bill, have you learned your abc and 123?";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);
    }


    /*
New Test Frame
Purpose: test if the -b parameter is working correctly"
 */
    @Test
    public void myMainTest25() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-b","Howdy","Hello","--",inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
new Test Frame
Purpose: Testing if the -f parameter works correctly
*/
    @Test
    public void myMainTest26() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "Let make it happen Unazi";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-f", "Unazi", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\",\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "Let make it happen Unazi";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
new Test Frame
Purpose: Testing if the -l parameter works correctly
*/
    @Test
    public void myMainTest27() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "Let make it happen Unazi";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-f", "Unazi", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\",\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "Let make it happen Unazi";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
new test case
Purpose: Testing that the -i and -l work together
*/
    @Test
    public void myMainTest28() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "-l", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say Hello unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
new test case
purpose : testing that the -i and -f works together correctly
*/
    @Test
    public void myMainTest29() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "and say howdy again\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "-f", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "and say howdy again\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
new test case
Purpose: Testing that the -i , -l and -b work together
*/
    @Test
    public void myMainTest30() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "-l", "-b", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say Hello unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }


    /*
new test case
purpose : testing that the -i,-f and -b works together correctly
*/
    @Test
    public void myMainTest31() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "and say howdy again\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        ;

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "-f", "-b", "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "and say howdy again\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
newly created test
purpose: Testing if parameter -i and -b works correctly when the from
contains enclosing quote
*/
    @Test
    public void myMainTest32() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }


    /*
newly created test
purpose: Testing if parameter -i and -b works correctly when the from
and To string contains double quote
*/
    @Test
    public void myMainTest33() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Howdy", "\"Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
newly created test
purpose: Testing if parameter -i and -b works correctly when the from and To string
are both enclosed by double quote
*/
    @Test
    public void myMainTest34() throws Exception {
        String input1 = "\"Howdy\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy\" unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Howdy\"", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello\" unazi again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }


    /*
Newly created test case
Purpose: testing if the -i , -l and -f parameter works correctly
*/
    @Test
    public void myMainTest35() throws Exception {
        String input1 = "Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final howdy unazi";
        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final Hello unazi";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Newly created test case
Purpose: testing if the -i , -l and -f parameter works correctly when the from string
contains double quote
*/
    @Test
    public void myMainTest36() throws Exception {
        String input1 = "\"Howdy Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final howdy unazi";
        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","\"Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Hello Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final howdy unazi";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Newly created test case
Purpose: testing if the -i , -l and -f parameter works correctly when the from and
To string contains double quote within it.
*/
    @Test
    public void myMainTest37() throws Exception {
        String input1 = "\"Howdy\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"howdy\" unazi";
        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","\"Howdy\"", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"Hello\" unazi";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
Newly created test case
Purpose: testing if the -i ,-b, -l and -f parameter works correctly when the from and
To string contains double quote within it.
*/
    @Test
    public void myMainTest38() throws Exception {
        String input1 = "\"Howdy\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"howdy\" unazi";
        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","\"Howdy\"", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"Hello\" unazi";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));


    }



    /*
Newly created test case
Purpose: testing if when  the from and To string contains double quote within it works
fine if there is no input option
*/
    @Test
    public void myMainTest39() throws Exception {
        String input1 = "\"Howdy\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"howdy\" unazi";
        File inputFile1 = createInputFile(input1);
        String args[] = {"\"Howdy\"", "\"Hello\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "\"Hello\" Unazi,\n" +
                "This is a test file for the replace utility\n" +
                "Again howdy Unazi this line for testing" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "final \"howdy\" unazi";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one
*/
    @Test
    public void myMainTest40() throws Exception {
        String input1 = "Howdy Unazi, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Billy\n" +
                "This is a test file for the replace utility\n" +
                "Billy this is going to be interesting, I tell you Billy\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }



    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and whether -i and -b parameter works well in this scenario
*/
    @Test
    public void myMainTest41() throws Exception {
        String input1 = "Howdy Unazi, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Billy\n" +
                "This is a test file for the replace utility\n" +
                "Billy this is going to be interesting, I tell you Billy\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and whether -i , -f and -b parameter works well in this scenario
*/
    @Test
    public void myMainTest42() throws Exception {
        String input1 = "Howdy Unazi, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-f","-b", "Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and whether -i , -l and -b parameter works well in this scenario
*/
    @Test
    public void myMainTest43() throws Exception {
        String input1 = "Howdy Unazi, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy unazi again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-b", "Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Unazi, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains one single quote
*/
    @Test
    public void myMainTest44() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one ,the from string contains one single quote and if the
parameter -i and -b works well in this scenario
*/
    @Test
    public void myMainTest45() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }



    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one ,the from string contains one single quote and if the
parameter -i, -l  and -b works well in this scenario
*/
    @Test
    public void myMainTest46() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b","-l", "\"Unazi", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy\" again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }



    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains is enclose with single quote
*/
    @Test
    public void myMainTest47() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Unazi\"", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains is enclose with single quote
and whether the -i and -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest48() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Unazi\"", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains is enclose with single quote
and whether the -i, -f and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest49() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-f","-b", "\"Unazi\"", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains is enclose with single quote
and whether the -i, -l and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest50() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-b", "\"Unazi\"", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string contains is enclose with single quote
and whether the -i, -l, -f and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest51() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","-b", "\"Unazi\"", "Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy Billy, let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy Billy again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the to and from string contains one single quote
*/
    @Test
    public void myMainTest52() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Unazi", "\"Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one ,the from and to String both contains one single quote and if the
parameter -i and -b works well in this scenario
*/
    @Test
    public void myMainTest53() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Unazi", "\"Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }



    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one ,the from string and To string  contains one single quote and if the
parameter -i, -l  and -b works well in this scenario
*/
    @Test
    public void myMainTest54() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Unazi", "\"Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }



    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string  and to string contains a  single quote
*/
    @Test
    public void myMainTest55() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Unazi", "\"Billy", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string and to String  contains is enclose with single quote
and whether the -i and -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest56() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-b", "\"Unazi\"", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string and to string is enclose with single quote
and whether the -i, -f and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest57() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-f","-b", "\"Unazi\"", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string  and To string enclose with double quote
and whether the -i, -l and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest58() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-b", "\"Unazi\"", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }


    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the from string and To String is enclose with double quote
and whether the -i, -l, -f and  -b parameter works correctly together in this scenario
*/
    @Test
    public void myMainTest59() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i","-l","-f","-b", "\"Unazi\"", "\"Billy\"", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy \"Billy\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"Billy\" again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    /*
New test case
purpose: Testing for a case where the number of occurrence on a single line
is more than one and the to string contains enclosing bracket
*/
    @Test
    public void myMainTest60() throws Exception {
        String input1 = "Howdy \"Unazi\", let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy \"unazi\" again!";

        File inputFile1 = createInputFile(input1);
        String args[] = {"-i", "\"Unazi\"", "(.,Billy)", "--", inputFile1.getPath()};
        Main.main(args);


        String expected1 = "Howdy (.,Billy), let's go Unazi\n" +
                "This is a test file for the replace utility\n" +
                "unazi this is going to be interesting, I tell you unazi\n"+
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say howdy (.,Billy) again!";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The files differ!", expected1, actual1);

    }

    @Test
    public void myMainTest61() throws Exception {
        String args[] = {"-a", "-b"};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest62() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        File inputFile2 = temporaryFolder.newFile("_tmpfile.bck");
        inputFile1.deleteOnExit();
        inputFile2.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        FileWriter fileWriter2 = new FileWriter(inputFile2);
        fileWriter1.write("Content of file1");
        fileWriter2.write("Content of file2");
        fileWriter1.close();
        fileWriter2.close();

        String args1[] = {"-b", "Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);

        String expected1 = "Content of file1";
        String actual1 = getFileContent(inputFile1.getPath());

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertEquals("Not performing replace for " + inputFile1.getName() + ": Backup file already exists", errStream.toString().trim());
    }


    @Test
    public void myMainTest63() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        File inputFile2 = temporaryFolder.newFile("_tmpfile.bck");
        inputFile1.deleteOnExit();
        inputFile2.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        FileWriter fileWriter2 = new FileWriter(inputFile2);
        fileWriter1.write("Content of file1");
        fileWriter2.write("Content of file2");
        fileWriter1.close();
        fileWriter2.close();

        String args1[] = {"Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);

        String expected1 = "This is file1";
        String actual1 = getFileContent(inputFile1.getPath());

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertEquals("The files differ!", expected1,actual1);
    }

    @Test
    public void myMainTest64() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
         inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-b","Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);

        String expected1 = "This is file1";
        String actual1 = getFileContent(inputFile1.getPath());
        String expected2 = "Content of file1";
        String actual2 = getFileContent(inputFile1.getPath() + ".bck");

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertEquals("The files differ!", expected1,actual1);
        assertEquals("The files differ!", expected2,actual2);

    }

    @Test
    public void myMainTest65() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-b","--", inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest66() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-b","Content","--", inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest67() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-f","Content","--", inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest68() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-l","Content","--", inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest69() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-i","Content","--", inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void myMainTest71() throws Exception {
        File inputFile = createInputFile9();
        String args[] = {};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    //Type d: gives indexOutOfBoundException instead of the general usage message

    @Test
    public void myMainTest72() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of -f -i file1");
        fileWriter1.close();

        String args1[] = {"-l"};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    //Type d: gives indexOutOfBoundException instead of the general usage message

    @Test
    public void myMainTest73() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of -f -i file1");
        fileWriter1.close();

        String args1[] = {"-f"};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    //Type d: gives indexOutOfBoundException instead of the general usage message
    @Test
    public void myMainTest74() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of -f -i file1");
        fileWriter1.close();

        String args1[] = {"-i"};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }



    //Type d: gives indexOutOfBoundException instead of the general usage message

    @Test

    public void myMainTest75() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of -f -i file1");
        fileWriter1.close();

        String args1[] = {"-b"};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    @Test

    public void myMainTest77() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of -f -i file1");
        fileWriter1.close();

        String args1[] = {"-f","--","-f","-i","-","--",inputFile1.getPath()};
        Main.main(args1);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


}