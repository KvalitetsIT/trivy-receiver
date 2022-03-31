package dk.kvalitetsit.hello.service;

import dk.kvalitetsit.hello.dao.TrivyDao;
import dk.kvalitetsit.hello.dao.entity.VulnerabilityCount;
import dk.kvalitetsit.hello.trivy.Result;
import dk.kvalitetsit.hello.trivy.Trivy;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TrivyServiceImpl implements TrivyService {
    private final TrivyDao trivyDao;

    public TrivyServiceImpl(TrivyDao trivyDao) {
        this.trivyDao = trivyDao;
    }

    @Override
    @Transactional
    public void helloServiceBusinessLogic(Trivy input) {
        var map = new HashMap<String, AtomicInteger>();
        input.getResults()
                .stream()
                .map(Result::getVulnerabilities)
                .flatMap(Collection::stream)
                .forEach(vuln -> map.computeIfAbsent(vuln.getSeverity(), (x) -> new AtomicInteger(0)).addAndGet(1));

        var result = new dk.kvalitetsit.hello.dao.entity.Result();
        result.setName(input.getArtifactName());
        result.setScanTime(OffsetDateTime.now());
        result.setCreated(Instant.now());

        var resultId = trivyDao.insertResult(result);

        map.forEach((k, v) -> {
            var vulnerabilityCount = new VulnerabilityCount();
            vulnerabilityCount.setResultId(resultId);
            vulnerabilityCount.setSeverity(k);
            vulnerabilityCount.setCount(v.longValue());

            trivyDao.insertVulnerabilityCount(vulnerabilityCount);
        });
    }
}
