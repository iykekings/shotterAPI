package dev.ikeze.Shotter.services;

import java.util.List;
import java.util.UUID;

import dev.ikeze.Shotter.model.Owner;

public interface OwnerService {
  List<Owner> findAll();

  Owner findById(UUID Id);

  Owner findByEmail(String email);

  Owner create(Owner owner);

  void authenticate(String username, String password) throws Exception;

  void delete(UUID Id);
}