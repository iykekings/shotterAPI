package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.services.UrlService;
import dev.ikeze.Shotter.utils.ApiError;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping(value = "urls", produces = { "application/json" })
@RestController
public class UrlController {

  @Autowired
  private UrlService urlService;

  @GetMapping
  public List<Url> getUrls() {
    return urlService.findAll();
  }

  @GetMapping(value = "{Id}", produces = { "application/json" })
  public ResponseEntity<?> getUrlById(@PathVariable("Id") long Id) {
    var url = urlService.findById(Id);
    if (url.isPresent()) {
      return new ResponseEntity<>(url.get(), HttpStatus.OK);
    }
    var error = new ApiError("Id " + Id + " doesn't exist", HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @GetMapping(value = "{ownerid}/owner")
  public List<Url> getAllUrlsByOwner(@PathVariable("ownerid") long ownerid) {
    return urlService.findByOwnerid(ownerid);
  }

  // POST: /urls
  @PostMapping
  public Url addUrl(@RequestBody Url url) {
    return urlService.addUrl(url);
  }

}