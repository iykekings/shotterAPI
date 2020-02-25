package dev.ikeze.Shotter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.repos.OwnerRepository;

@Transactional
@Service(value = "ownerService")
public class OwnerServiceImp implements OwnerService {

  @Autowired
  private OwnerRepository ownerRepository;

  @Override
  public List<Owner> findAll() {
    // TODO Auto-generated method stub
    return null;
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

}