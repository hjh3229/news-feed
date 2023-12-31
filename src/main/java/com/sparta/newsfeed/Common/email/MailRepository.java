package com.sparta.newsfeed.Common.email;

import com.sparta.newsfeed.Common.email.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Mail,Long>{
    Optional<Mail> findByEmail(String email);

   // Optional<Mail> findByEmail_code(String code);

    boolean existsByEmail(String email);
}
