package net.thomasnardone.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testInsert() {
		int[] array = { 1, 2, 3 };
		int[] test0 = { 4, 1, 2, 3 };
		int[] test1 = { 1, 4, 2, 3 };
		int[] test2 = { 1, 2, 4, 3 };
		int[] test3 = { 1, 2, 3, 4 };

		Assert.assertArrayEquals(test0, ArrayUtil.insert(4, array, 0));
		Assert.assertArrayEquals(test1, ArrayUtil.insert(4, array, 1));
		Assert.assertArrayEquals(test2, ArrayUtil.insert(4, array, 2));
		Assert.assertArrayEquals(test3, ArrayUtil.insert(4, array, 3));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertException1() {
		int[] array = { 1, 2, 3 };
		ArrayUtil.insert(4, array, -1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInsertException2() {
		int[] array = { 1, 2, 3 };
		ArrayUtil.insert(4, array, 4);
	}

	@Test
	public void testRemove() {
		int[] array = { 1, 2, 3 };
		int[] test0 = { 2, 3 };
		int[] test1 = { 1, 3 };
		int[] test2 = { 1, 2 };

		Assert.assertArrayEquals(test0, ArrayUtil.remove(array, 0));
		Assert.assertArrayEquals(test1, ArrayUtil.remove(array, 1));
		Assert.assertArrayEquals(test2, ArrayUtil.remove(array, 2));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveException1() {
		int[] array = { 1, 2, 3 };
		ArrayUtil.remove(array, -1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveException2() {
		int[] array = { 1, 2, 3 };
		ArrayUtil.remove(array, 3);
	}
}
