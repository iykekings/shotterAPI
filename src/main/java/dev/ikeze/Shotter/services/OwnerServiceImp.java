package dev.ikeze.Shotter.services;

import java.util.ArrayList;
import java.util.List;

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

import dev.ikeze.Shotter.error.OwnerMissingFieldsException;
import dev.ikeze.Shotter.error.OwnerNotFoundException;
import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;

@Transactional
@Service(value = "ownerService")
public class OwnerServiceImp implements OwnerService, UserDetailsService {

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public List<Owner> findAll() {
    return (List<Owner>) ownerRepository.findAll();
  }

  @Override
  public Owner findById(long Id) {
    return ownerRepository.findById(Id).orElseThrow(() -> new OwnerNotFoundException(Long.toString(Id)));
  }

  @Override
  public Owner findByEmail(String email) {
    return ownerRepository.findByEmail(email).orElseThrow(() -> new OwnerNotFoundException(email));
  }

  @Override
  public Owner create(Owner owner) {
    owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
    return ownerRepository.save(owner);
  }

  @Override
  public void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }

  @Override
  public void delete(long Id) {
    // TODO Auto-generated method stub

  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return ownerRepository.findByEmail(username).map(u -> new User(u.getEmail(), u.getPassword(), new ArrayList<>()))
        .orElseGet(() -> {
          throw new UsernameNotFoundException("User not found with email: " + username);
        });
  }

  private static void checkOwner(Owner owner) {
    if (owner.getEmail().isBlank() || owner.getPassword().isBlank() || owner.getName().isBlank()) {
      throw new OwnerMissingFieldsException();
    }
  }

}