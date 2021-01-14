package com.fileee.payroll.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SortUtils {

    /**
     * Sorts a given list of pojo objects using bubble sort algorithm. The sort key type must be comparable, e.g.
     * Integer, String, BigDecimal, etc.
     * <p>
     * Every parameter is mandatory, if any is <tt>null</tt>, this method will throw  <tt>NullPointerException</tt>.
     * </p>
     *
     * @param keyExtractor  function that returns the object property to sort the list.
     * @param keyComparator comparator function that compares the sort key.
     * @param order         for the list to be sorted, ascending (ASC) or descending (DESC).
     * @param list          to be sorted.
     * @param <E>           type of element to be compared.
     * @param <P>           type of the sort key.
     * @return the sorted list.
     * @throws NullPointerException if either argument is null
     */
    public static <E, P> List<E> sortBy(Function<E, P> keyExtractor, Comparator<P> keyComparator, SortOrder order, List<E> list) {
        E aux;
        //noinspection unchecked
        E[] array = (E[]) list.toArray();
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                P value1 = keyExtractor.apply(array[j - 1]);
                P value2 = keyExtractor.apply(array[j]);
                int compareResult = keyComparator.compare(value1, value2);
                if ((order.equals(SortOrder.ASC) && compareResult > 0) || (order.equals(SortOrder.DESC) && compareResult < 0)) {
                    aux = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = aux;
                }
            }
        }
        return Arrays.asList(array);
    }

    public enum SortOrder {
        ASC, DESC
    }

}
