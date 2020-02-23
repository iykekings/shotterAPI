package dev.ikeze.Shotter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.repos.UrlRepository;
import org.springframework.web.bind.annotation.GetMapping;

@RequestMapping("url")
@RestController
public class UrlController {
  private UrlRepository urlRepository;

  @Autowired
  public UrlController(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  @GetMapping
  public List<Url> getUrls() {
    return (List<Url>) urlRepository.findAll();
  }
}