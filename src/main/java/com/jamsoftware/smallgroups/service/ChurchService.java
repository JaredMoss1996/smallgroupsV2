package com.jamsoftware.smallgroups.service;

import com.jamsoftware.smallgroups.model.Church;
import com.jamsoftware.smallgroups.repository.ChurchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChurchService {

    private final ChurchRepository churchRepository;

    public ChurchService(ChurchRepository churchRepository) {
        this.churchRepository = churchRepository;
    }

    public List<Church> findAllChurches() {
        return churchRepository.findAllChurches();
    }

    public Map<Long, Church> findAllChurchesMapById() {
        return findAllChurches().stream()
                .collect(Collectors.toMap(Church::getId, Function.identity()));
    }
}

