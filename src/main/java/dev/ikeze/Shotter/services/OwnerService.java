package dev.ikeze.Shotter.services;

import java.util.List;

import dev.ikeze.Shotter.model.Owner;

public interface OwnerService {
  List<Owner> findAll();

  Owner findById(long Id);

  Owner findByEmail(String email);

  Owner create(Owner owner);

  String login(Owner owner);

  void delete(long Id);
}