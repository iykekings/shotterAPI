package dev.ikeze.Shotter.util;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
  UUID getOwnerid();
}