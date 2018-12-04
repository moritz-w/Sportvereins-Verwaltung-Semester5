package at.fhv.sportsclub.controller.common;

import java.util.List;

public interface Controller<T, E, R> {

    List<T> getAll();

    R saveOrUpdate(T dto, CommonController.BeforeMapHook<T> beforeMapHook, CommonController.BeforeSaveHook<E> beforeSaveHook);

    R saveOrUpdate(T dto);

    T getDetails(String id, boolean full);
}

