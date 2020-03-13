package dev.ikeze.Shotter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.model.Owner;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.OwnerRepository;
import dev.ikeze.Shotter.repos.UrlRepository;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private OwnerRepository ownerRepository;

  @Autowired
  private UrlRepository urlRepository;

  @Override
  public void run(String... args) throws Exception {

    Owner o1 = new Owner("test", "test@email.com", bCryptPasswordEncoder.encode("password"));
    Owner o2 = new Owner("test1", "test1@email.com", bCryptPasswordEncoder.encode("password1"));
    Owner o3 = new Owner("test2", "test2@email.com", bCryptPasswordEncoder.encode("password2"));

    Url u0 = new Url("projects", "https://ikeze.dev/projects", o1);
    Url u1 = new Url("github", "https://github.com", o1);
    Url u2 = new Url("iykekings", "https://github.com/iykekings", o2);
    Url u3 = new Url("matthardsman", "https://github.com/mattshardman", o2);
    Url u4 = new Url("lasjs", "https://www.npmjs.com/package/las-js", o3);

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