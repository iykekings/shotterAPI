package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.services.UrlService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public Url getUrlById(@PathVariable("Id") long Id) {
    return urlService.findById(Id);
  }

  // "/1/owner"
  @GetMapping(value = "{ownerid}/owner")
  public List<Url> getAllUrlsByOwner(@PathVariable("ownerid") long ownerid) {
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

  // DELETE: /urls/Id
  @DeleteMapping(value = "{Id}")
  public void delete(@PathVariable("Id") Long Id) {
    urlService.deleteById(Id);
  }

}