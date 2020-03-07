package dev.ikeze.Shotter.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, UUID> {
  // public List<Url> find
  public Optional<Owner> findByEmail(String email);
}