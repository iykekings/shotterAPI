package dev.ikeze.Shotter.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
}