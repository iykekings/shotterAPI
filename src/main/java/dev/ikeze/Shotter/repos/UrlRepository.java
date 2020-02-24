package dev.ikeze.Shotter.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
  public List<Url> findByOwnerOwnerid(long ownerid);

  public Url findByDirectory(String directory);
}