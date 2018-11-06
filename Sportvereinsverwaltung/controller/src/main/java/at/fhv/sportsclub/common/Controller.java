package at.fhv.sportsclub.common;

import java.util.List;

public interface Controller<T, R> {

    List<T> getAll();

    R saveOrUpdate(T dto);
}
