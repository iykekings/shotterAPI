package dev.ikeze.Shotter.controllers;

import dev.ikeze.Shotter.model.AuthenticationRequest;
import dev.ikeze.Shotter.model.AuthenticationResponse;
import dev.ikeze.Shotter.model.Exists;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.services.OwnerServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping(value = "owners")
@RestController
public class OwnerController {

  private final OwnerServiceImp ownerService;
  public OwnerController(OwnerServiceImp ownerService) {
    this.ownerService = ownerService;
  }

  @GetMapping()
  public List<Owner> getAllOwners() {
    return ownerService.findAll();
  }

  @GetMapping(value = "{Id}")
  public Owner getOwnerById(@PathVariable UUID Id) {
    var owner = ownerService.findById(Id);
    // TODO: find better way to ignore password return
    owner.setPassword(null);
    return owner;
  }

  @GetMapping(value = "check/{email}")
  public ResponseEntity<?> checkIfOwnerExists(@PathVariable String email) {
    try {
      ownerService.findByEmail(email);
      return ResponseEntity.ok(new Exists(true));
    } catch (Exception e) {
      return ResponseEntity.ok(new Exists(false));
    }
  }

  @PostMapping(value = "login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    ownerService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final String token = ownerService.VerifyUser(authenticationRequest.getUsername());
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

  @PostMapping(value = "create")
  public ResponseEntity<?> create(@RequestBody Owner owner) {
    return ResponseEntity.created(URI.create("/owners/create")).body(ownerService.create(owner));
  }

  // TODO: Second release cycle
  // @DeleteMapping(value = "{id}")
  // public void deleteOwner(@PathVariable long Id) {

  // }
  // TODO: second release cycle
  // Update owner

}
