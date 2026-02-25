package com.rentalhouse.app.services;

import java.util.List;

public interface IGenericService<T> {

   public List<T> getAll();

   public T getById(String id);

   public boolean create(T entity);

   public boolean update(T entity);

   public boolean delete(String id);
}
