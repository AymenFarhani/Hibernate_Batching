package com.batching.startup;

import com.batching.entities.Person;
import com.batching.services.PersonBatchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PersonBatchService batchService;

    public DataLoader(PersonBatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public void run(String... args) throws Exception {
        final int total = 10000; // number of rows to insert
        List<Person> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            list.add(Person.builder()
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .email("user" + i + "@example.com")
                    .build());
        }

        long start = System.currentTimeMillis();
        batchService.insertBatch(list);
        long end = System.currentTimeMillis();
        System.out.printf("Inserted %d rows in %d ms%n", total, (end - start));
    }
}
