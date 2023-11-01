package com.mborg.mailMerge.repository;

import com.mborg.mailMerge.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    @author monChrome
 */
@Repository
public interface RecipientsRepo extends JpaRepository<Recipient, Long> {
}
