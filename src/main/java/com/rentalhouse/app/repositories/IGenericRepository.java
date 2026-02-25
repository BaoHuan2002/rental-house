package com.rentalhouse.app.repositories;

import java.util.List;

public interface IGenericRepository<T> {
   public List<T> getAll();

   public T getById(String id);

   public boolean create(T entity);

   public boolean update(T entity);

   public boolean delete(String id);
}
