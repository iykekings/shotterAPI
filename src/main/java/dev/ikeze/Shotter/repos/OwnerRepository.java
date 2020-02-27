package dev.ikeze.Shotter.repos;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
  // public List<Url> find
  public Optional<Owner> findByEmail(String email);
}