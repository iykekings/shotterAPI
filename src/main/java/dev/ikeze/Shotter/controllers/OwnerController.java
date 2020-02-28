package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.AuthenticationRequest;
import dev.ikeze.Shotter.model.AuthenticationResponse;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.services.OwnerServiceImp;
import dev.ikeze.Shotter.util.JwtUtil;

@RequestMapping(value = "owners")
@RestController
public class OwnerController {

  @Autowired
  private OwnerServiceImp ownerService;

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping()
  public List<Owner> getAllOwners() {
    return (List<Owner>) ownerService.findAll();
  }

  @PostMapping(value = "login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    ownerService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails = ownerService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

  @PostMapping(value = "create")
  public Owner create(@RequestBody Owner owner) {
    return ownerService.create(owner);
  }

}