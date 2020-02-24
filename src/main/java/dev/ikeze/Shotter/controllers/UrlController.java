package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.UrlRepository;
import dev.ikeze.Shotter.utils.ApiError;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping(value = "urls", produces = { "application/json" })
@RestController
public class UrlController {

  @Autowired
  private UrlRepository urlRepository;

  @GetMapping
  public List<Url> getUrls() {
    return (List<Url>) urlRepository.findAll();
  }

  @GetMapping(value = "{Id}")
  public ResponseEntity<?> getUrlById(@PathVariable("Id") long Id) {
    var url = urlRepository.findById(Id);
    if (url.isPresent())
      return ResponseEntity.ok(url.get());
    var error = new ApiError("Id: " + Id + " doesn't exist", HttpStatus.NOT_FOUND);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @GetMapping(value = "{ownerid}/owner")
  public List<Url> getAllUrlsByOwner(@PathVariable("ownerid") long ownerid) {
    return urlRepository.findByOwnerOwnerid(ownerid);
  }

}