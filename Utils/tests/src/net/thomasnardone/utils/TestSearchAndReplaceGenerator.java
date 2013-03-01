package net.thomasnardone.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import net.thomasnardone.utils.testtools.SimpleSearchAndReplace;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSearchAndReplaceGenerator {
	private static final String	SOURCE_FILE	= "tests/res/gen/ObjectSearchAndReplaceClass.java";

	@Before
	public void setupCode() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tests/res/SearchAndReplaceClass.txt"));
		PrintStream writer = new PrintStream(SOURCE_FILE);
		String line = reader.readLine();
		while (line != null) {
			writer.println(line);
			line = reader.readLine();
		}
		writer.flush();
		writer.close();
	}

	@Test(timeout = 2000)
	public void testMethodReplacement() throws Exception {
		new SimpleSearchAndReplace("java.lang.Object", "gen", true).generate();
		compareFiles(SOURCE_FILE, "tests/res/ReplacedWithAnnotation.txt");
		new File(SOURCE_FILE).delete();
	}

	@Test(timeout = 2000)
	public void testMethodReplacementNoAnnotations() throws Exception {
		new SimpleSearchAndReplace("java.lang.Object", "gen", false).generate();
		compareFiles(SOURCE_FILE, "tests/res/ReplacedWithoutAnnotation.txt");
		new File(SOURCE_FILE).delete();
	}

	private void compareFiles(final String actualFile, final String expectedFile) throws FileNotFoundException, IOException {
		BufferedReader actualReader = new BufferedReader(new FileReader(actualFile));
		BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFile));
		String actual = actualReader.readLine();
		String expected = expectedReader.readLine();
		int count = 1;
		while (expected != null) {
			Assert.assertNotNull(actual);
			Assert.assertEquals("Line " + count, expected, actual);
			actual = actualReader.readLine();
			expected = expectedReader.readLine();
			count++;
		}
		Assert.assertNull(actualReader.readLine());
		actualReader.close();
		expectedReader.close();
	}
}
