package dev.ikeze.Shotter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;

@Transactional
@Service(value = "ownerService")
public class OwnerServiceImp implements OwnerService, UserDetailsService {

  @Autowired
  private OwnerRepository ownerRepository;

  @Override
  public List<Owner> findAll() {
    return (List<Owner>) ownerRepository.findAll();
  }

  @Override
  public Owner findById(long Id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Owner findByEmail(String email) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Owner create(Owner owner) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String login(Owner owner) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(long Id) {
    // TODO Auto-generated method stub

  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return ownerRepository.findByEmail(username).map(u -> new User(u.getEmail(), u.getPassword(), new ArrayList<>()))
        .orElseGet(() -> {
          throw new UsernameNotFoundException(username);
        });
  }

}