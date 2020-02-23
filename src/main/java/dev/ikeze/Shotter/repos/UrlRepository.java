package dev.ikeze.Shotter.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ikeze.Shotter.model.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
}