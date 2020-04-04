package dev.ikeze.Shotter.controllers;

import dev.ikeze.Shotter.repos.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "r")
@RestController
public class RedirectController {

  @Autowired
  private UrlRepository urlRepository;

  @Value("${ikeze.frontendUrl}")
  private String frontendUrl;

  @GetMapping(value = "{directory}")
  public ModelAndView redirectToNewUrl(@PathVariable String directory) {
    return urlRepository.findByDirectory(directory).map(x -> {
      x.setClicks(x.getClicks() + 1);
      urlRepository.save(x);
      return new ModelAndView(String.format("redirect:%s", x.getRedirect()));
    }).orElseGet(() -> new ModelAndView("redirect:" + frontendUrl + "/404"));
  }

}