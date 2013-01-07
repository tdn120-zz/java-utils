package net.thomasnardone.utils;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestListUtil {
	@Test
	public void testListsContainSameItems() {
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(1, 3, 2)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(2, 1, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(2, 3, 1)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(3, 1, 2)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(3, 2, 1)));

		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(1, 3, 2), Arrays.asList(1, 2, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(2, 1, 3), Arrays.asList(1, 2, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(2, 3, 1), Arrays.asList(1, 2, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(3, 1, 2), Arrays.asList(1, 2, 3)));
		Assert.assertTrue(ListUtil.listsContainSameItems(Arrays.asList(3, 2, 1), Arrays.asList(1, 2, 3)));

		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3, 4)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(2, 3)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(2, 3), Arrays.asList(1, 2, 3)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 4)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(1, 4, 2)));
		Assert.assertFalse(ListUtil.listsContainSameItems(Arrays.asList(1, 2, 3), Arrays.asList(4, 2, 1)));
	}
}
