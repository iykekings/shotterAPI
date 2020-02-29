package dev.ikeze.Shotter.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.error.UrlDuplicateException;
import dev.ikeze.Shotter.error.UrlMissingFieldsException;
import dev.ikeze.Shotter.error.UrlNotFoundException;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.UrlRepository;

@Transactional
@Service(value = "urlService")
public class UrlService {

  @Autowired
  private UrlRepository urlRepository;

  // returns Url if successful and null when the Url already exists
  public Url addUrl(Url url) {
    checkUrl(url);
    var urlInDB = urlRepository.findByDirectory(url.getDirectory());
    if (urlInDB.isEmpty()) {
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

  public void deleteById(long Id) {
    try {
      urlRepository.deleteById(Id);
    } catch (Exception e) {
      throw new UrlNotFoundException(Long.toString(Id));
    }
  }

  public Url updateById(long Id, Url url) {
    checkUrl(url);
    Url urlInDB = findById(Id);
    urlInDB.setDirectory(url.getDirectory());
    urlInDB.setRedirect(url.getRedirect());
    urlInDB.getOwner().setOwnerid(url.getOwner().getOwnerid());
    return urlRepository.save(urlInDB);
  }

  private static void checkUrl(Url url) {
    var d = url.getDirectory();
    var r = url.getRedirect();
    if (d == null || d.isBlank() || r == null || r.isBlank()) {
      throw new UrlMissingFieldsException();
    }
  }
}