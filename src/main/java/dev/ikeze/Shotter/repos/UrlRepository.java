package dev.ikeze.Shotter.repos;

import org.springframework.data.repository.CrudRepository;

import dev.ikeze.Shotter.model.Url;

public interface UrlRepository extends CrudRepository<Url, Long> {
}