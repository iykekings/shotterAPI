package dev.ikeze.Shotter.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Exists;
import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.services.UrlService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping(value = "urls")
@RestController
public class UrlController {

  @Autowired
  private UrlService urlService;

  // "/" ✅
  @GetMapping
  public List<Url> getUrls() {
    return urlService.findAll();
  }

  // "/5" ✅
  @GetMapping(value = "{Id}")
  public Url getUrlById(@PathVariable("Id") UUID Id) {
    return urlService.findById(Id);
  }

  // "/1/owner"
  @GetMapping(value = "{ownerid}/owner")
  public List<Url> getAllUrlsByOwner(@PathVariable("ownerid") UUID ownerid) {
    return urlService.findByOwnerid(ownerid);
  }

  // dir/directory
  @GetMapping(value = "dir/{directory}")
  public Url getUrlByDirectory(@PathVariable("directory") String directory) {
    return urlService.findByDirectory(directory);
  }

  // POST: /urls
  @PostMapping
  public Url addUrl(@RequestBody Url url) {
    return urlService.addUrl(url);
  }

  // DELETE: /Id
  @DeleteMapping(value = "{Id}")
  public void delete(@PathVariable("Id") UUID Id) {
    urlService.deleteById(Id);
  }

  // PUT: /Id
  @PutMapping(value = "{Id}")
  public Url update(@PathVariable("Id") UUID Id, @RequestBody Url url) {
    return urlService.updateById(Id, url);
  }

  @GetMapping(value = "check/{directory}")
  public ResponseEntity<?> checkIfOwerExists(@PathVariable String directory) {
    try {
      urlService.findByDirectory(directory);
      return ResponseEntity.ok(new Exists(true));
    } catch (Exception e) {
      return ResponseEntity.ok(new Exists(false));
    }
  }

}