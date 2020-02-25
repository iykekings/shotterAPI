package dev.ikeze.Shotter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.repos.UrlRepository;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
  private OwnerRepository ownerRepository;
  private UrlRepository urlRepository;

  @Autowired
  public SeedData(OwnerRepository ownerRepository, UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
    this.ownerRepository = ownerRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    Owner o1 = new Owner("test", "test@email.com", "password");
    Owner o2 = new Owner("test1", "test1@email.com", "password1");
    Owner o3 = new Owner("test2", "test2@email.com", "password2");

    Url u0 = new Url("testing0", "https://testing0", o1);
    Url u1 = new Url("testing1", "https://testing1", o1);
    Url u2 = new Url("testing2", "https://testing2", o2);
    Url u3 = new Url("testing3", "https://testing3", o2);
    Url u4 = new Url("testing4", "https://testing4", o3);

    ownerRepository.save(o1);
    ownerRepository.save(o2);
    ownerRepository.save(o3);

    urlRepository.save(u0);
    urlRepository.save(u1);
    urlRepository.save(u2);
    urlRepository.save(u3);
    urlRepository.save(u4);

    urlRepository.findAll().forEach(System.out::println);
    ownerRepository.findAll().forEach(System.out::println);
  }

}