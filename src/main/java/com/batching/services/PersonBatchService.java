package com.batching.services;


import com.batching.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonBatchService {

    private final EntityManager entityManager;
    private static final int BATCH_SIZE = 50; // align with hibernate.jdbc.batch_size

    public PersonBatchService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void insertBatch(List<Person> people) {
        int i = 0;
        for (Person p : people) {
            entityManager.persist(p);
            i++;
            if (i % BATCH_SIZE == 0) {
                // flush to send statements to DB, then clear persistence context
                entityManager.flush();
                entityManager.clear();
            }
        }
        // flush remaining
        entityManager.flush();
        entityManager.clear();
    }
}


