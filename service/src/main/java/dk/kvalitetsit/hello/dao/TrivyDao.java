package dk.kvalitetsit.hello.dao;

import dk.kvalitetsit.hello.dao.entity.Result;
import dk.kvalitetsit.hello.dao.entity.VulnerabilityCount;

public interface TrivyDao {
    Long insertResult(Result result);
    Long insertVulnerabilityCount(VulnerabilityCount vulnerabilityCount);

}