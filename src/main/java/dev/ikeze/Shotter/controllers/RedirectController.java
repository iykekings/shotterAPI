package dev.ikeze.Shotter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.ikeze.Shotter.model.Url;
import dev.ikeze.Shotter.services.UrlService;

@RequestMapping(value = "r")
@RestController
public class RedirectController {
  @Autowired
  private UrlService urlService;

  @GetMapping(value = "{directory}")
  public ModelAndView redirectToNewUrl(@PathVariable String directory) {
    Url fullUrl = urlService.findByDirectory(directory);
    fullUrl.setClicks(fullUrl.getClicks() + 1);
    urlService.updateById(fullUrl.getId(), fullUrl);
    return new ModelAndView(String.format("redirect:%s", fullUrl.getRedirect()));
  }
}