package dev.ikeze.Shotter.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ikeze.Shotter.error.UrlDuplicateException;
import dev.ikeze.Shotter.error.UrlInvalidException;
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
    try {
      Document doc = Jsoup.connect(url.getRedirect()).get();
      Element tit1 = doc.select("meta[property=og:title]").first();
      Element tit2 = doc.select("meta[name=twitter:title]").first();
      String tit3 = doc.title();
      String title = !(tit1 == null) ? tit1.attr("content") : !(tit2 == null) ? tit2.attr("content") : tit3;
      url.setTitle(title);

      Element descr1 = doc.select("meta[property=og:description]").first();
      Element descr2 = doc.select("meta[name=twitter:description]").first();
      String description = !(descr1 == null) ? descr1.attr("content") : !(descr2 == null) ? descr2.attr("content") : "";
      url.setDescription(description);

      Element img1 = doc.select("meta[property=og:image]").first();
      Element img2 = doc.select("meta[name=twitter:image]").first();
      String image = !(img1 == null) ? img1.attr("content") : !(img2 == null) ? img2.attr("content") : "";
      System.out.println("\nTitle: " + title + " Description: " + description + " Image: " + image + "\n");
      url.setImage(image);

    } catch (IOException e) {
      throw new UrlInvalidException(url.getRedirect());
    }
    var urlInDB = urlRepository.findByDirectory(url.getDirectory());
    if (urlInDB.isEmpty()) {
      return urlRepository.save(url);
    }
    throw new UrlDuplicateException(url.getDirectory());
  }

  public Url findById(UUID uuid) {
    return urlRepository.findById(uuid).orElseThrow(() -> new UrlNotFoundException(uuid.toString()));
  }

  public Url findByDirectory(String directory) {
    return urlRepository.findByDirectory(directory).orElseThrow(() -> new UrlNotFoundException(directory));
  }

  public List<Url> findAll() {
    return (List<Url>) urlRepository.findAll();
  }

  public List<Url> findByOwnerid(UUID ownerid) {
    return urlRepository.findByOwnerOwnerid(ownerid);
  }
  public List<Url> findByOwnerEmail(String email) {
    return urlRepository.findByOwnerEmail(email);
  }

  public void deleteById(UUID Id) {
    try {
      urlRepository.deleteById(Id);
    } catch (Exception e) {
      throw new UrlNotFoundException(Id.toString());
    }
  }

  public Url updateById(UUID uuid, Url url) {
    checkUrl(url);
    Url urlInDB = findById(uuid);
    urlInDB.setDirectory(url.getDirectory());
    urlInDB.setRedirect(url.getRedirect());
    urlInDB.getOwner().setOwnerid(url.getOwner().getOwnerid());
    return urlRepository.save(urlInDB);
  }

  private static void checkUrl(Url url) {
    String d = url.getDirectory();
    String r = url.getRedirect();
    if (d == null || d.isBlank() || r == null || r.isBlank()) {
      throw new UrlMissingFieldsException();
    }
  }
}