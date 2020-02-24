package dev.ikeze.Shotter.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.error.UrlDuplicateException;
import dev.ikeze.Shotter.error.UrlNotFoundException;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.UrlRepository;

@Transactional
@Service(value = "urlService")
public class UrlService {

  @Autowired
  private UrlRepository urlRepository;

  // returns Url if successful and null when the Url already exists
  @Transactional
  public Url addUrl(Url url) {
    var urlInDB = urlRepository.findByDirectory(url.getDirectory());
    if (urlInDB == null) {
      return urlRepository.save(url);
    }
    throw new UrlDuplicateException(url.getDirectory());
  }

  public Url findById(long Id) {
    return urlRepository.findById(Id).orElseThrow(() -> new UrlNotFoundException(Long.toString(Id)));
  }

  public Url findByDirectory(String directory) {
    return urlRepository.findByDirectory(directory).orElseThrow(() -> new UrlNotFoundException(directory));
  }

  public List<Url> findAll() {
    return (List<Url>) urlRepository.findAll();
  }

  public List<Url> findByOwnerid(long ownerid) {
    return urlRepository.findByOwnerOwnerid(ownerid);
  }
}