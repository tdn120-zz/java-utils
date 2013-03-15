package net.thomasnardone.utils;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class TestStringUtil {

	@Test
	public void testFromCsv() {
		Assert.assertArrayEquals(new String[] { "one", "two" }, StringUtil.fromCsv("one,two"));
		Assert.assertArrayEquals(new String[0], StringUtil.fromCsv(""));
		Assert.assertArrayEquals(new String[0], StringUtil.fromCsv(null));
	}

	@Test
	public void testToCsv() {
		Assert.assertEquals("one,two", StringUtil.toCsv(Arrays.asList("one", "two")));
		Assert.assertEquals("", StringUtil.toCsv(new LinkedList<String>()));
		Assert.assertEquals("", StringUtil.toCsv(null));
	}
}
