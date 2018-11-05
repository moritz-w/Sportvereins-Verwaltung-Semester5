package at.fhv.sportsclub.common;

import java.util.List;

public interface Controller<T> {

    List<T> getAll();

    boolean saveOrUpdate(T dto);
}
