package dk.kvalitetsit.hello.trivy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
    @JsonProperty("Vulnerabilities")
    private List<Vulnerability> vulnerabilities;

    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
}
