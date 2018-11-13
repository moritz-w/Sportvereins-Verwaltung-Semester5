package at.fhv.sportsclub.controller.common;

import java.util.List;

public interface Controller<T, R> {

    List<T> getAll();

    R saveOrUpdate(T dto);

    T getDetails(String id);
}
