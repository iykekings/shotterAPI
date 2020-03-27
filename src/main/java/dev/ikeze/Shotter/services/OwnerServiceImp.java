package dev.ikeze.Shotter.services;

import dev.ikeze.Shotter.error.LoginException;
import dev.ikeze.Shotter.error.OwnerDuplicateException;
import dev.ikeze.Shotter.error.OwnerMissingFieldsException;
import dev.ikeze.Shotter.error.OwnerNotFoundException;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service(value = "ownerService")
public class OwnerServiceImp implements OwnerService, UserDetailsService {

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public List<Owner> findAll() {
    return (List<Owner>) ownerRepository.findAll();
  }

  @Override
  public Owner findById(UUID Id) {
    return ownerRepository.findById(Id).orElseThrow(() -> new OwnerNotFoundException(Id.toString()));
  }

  @Override
  public Owner findByEmail(String email) {
    return ownerRepository.findByEmail(email).orElseThrow(() -> new OwnerNotFoundException(email));
  }

  @Override
  public Owner create(Owner owner) {
    checkOwner(owner);
    Optional<Owner> ownerInDB = ownerRepository.findByEmail(owner.getEmail());
    if (ownerInDB.isEmpty()) {
      owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
      return ownerRepository.save(owner);
    }
    throw new OwnerDuplicateException(owner.getEmail());
  }

  @Override
  public void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new LoginException();
    }
  }

  @Override
  public void delete(UUID Id) {
    try {
      ownerRepository.deleteById(Id);
    } catch (Exception e) {
      throw new OwnerNotFoundException(Id.toString());
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return ownerRepository.findByEmail(username).map(u -> new User(u.getEmail(), u.getPassword(), new ArrayList<>()))
        .orElseGet(() -> {
          throw new UsernameNotFoundException("User not found with email: " + username);
        });
  }

  public String VerifyUser(String username) {
    var user =  ownerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    final UserDetails userDetails = new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    Map<String, Object> claims = new HashMap<>();
    claims.put("Id", user.getOwnerid());
    return jwtUtil.generateToken(userDetails, claims);
  }

  private static void checkOwner(Owner owner) {
    String e = owner.getEmail();
    String p = owner.getPassword();
    String n = owner.getName();
    String error = "";
    if (e == null || e.isBlank()) {
      error = "email";
    }
    if (p == null || p.isBlank()) {
      error = error.length() > 0 ? error + ", password" : "password";
    }
    if (n == null || n.isBlank()) {
      error = error.length() > 0 ? error + "and name" : "name";
    }
    if (error.length() > 0)
      throw new OwnerMissingFieldsException(error);
  }

}