package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;

@RequestMapping("owner")
@RestController
public class OwnerController {
  final OwnerRepository ownerRepository;

  @Autowired
  public OwnerController(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @GetMapping()
  public List<Owner> getAllOwners() {
    return (List<Owner>) ownerRepository.findAll();
  }

}