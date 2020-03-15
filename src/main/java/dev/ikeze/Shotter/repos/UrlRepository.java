package dev.ikeze.Shotter.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url, UUID> {
  public List<Url> findByOwnerOwnerid(UUID ownerid);
  public List<Url> findByOwnerEmail(String email);

  public Optional<Url> findByDirectory(String directory);
}