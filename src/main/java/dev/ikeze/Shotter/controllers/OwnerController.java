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

import dev.ikeze.Shotter.model.AuthenticationResponse;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.services.OwnerServiceImp;
import dev.ikeze.Shotter.util.JwtUtil;

@RequestMapping("owners")
@RestController
public class OwnerController {

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private OwnerServiceImp ownerService;

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping()
  public List<Owner> getAllOwners() {
    return (List<Owner>) ownerRepository.findAll();
  }

  @PostMapping(value = "login")
  public ResponseEntity<?> login(@RequestBody Owner owner) throws Exception {
    ownerService.authenticate(owner.getEmail(), owner.getPassword());
    final UserDetails userDetails = ownerService.loadUserByUsername(owner.getEmail());
    final String token = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(token));
  }

}