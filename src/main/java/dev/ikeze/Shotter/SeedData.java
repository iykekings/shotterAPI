package dev.ikeze.Shotter;

import org.springframework.boot.CommandLineRunner;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.repos.UrlRepository;

public class SeedData implements CommandLineRunner {
  private OwnerRepository ownerRepository;
  private UrlRepository urlRepository;

  public SeedData(OwnerRepository ownerRepository, UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
    this.ownerRepository = ownerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Url u0 = new Url("testing0");
    Url u1 = new Url("testing1");
    Url u2 = new Url("testing2");
    Url u3 = new Url("testing3");
    Url u4 = new Url("testing4");

    Owner o1 = new Owner("test", "test@email.com", "password");
    Owner o2 = new Owner("test1", "test1@email.com", "password1");
    Owner o3 = new Owner("test2", "test2@email.com", "password2");

    urlRepository.save(u0);
    urlRepository.save(u1);
    urlRepository.save(u2);
    urlRepository.save(u3);
    urlRepository.save(u4);

    ownerRepository.save(o1);
    ownerRepository.save(o2);
    ownerRepository.save(o3);

  }

}