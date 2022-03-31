package dk.kvalitetsit.hello.dao.entity;

import java.time.Instant;
import java.time.OffsetDateTime;

public class Result {
    private Long id;
    private String name;
    private OffsetDateTime scanTime;
    private Instant created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getScanTime() {
        return scanTime;
    }

    public void setScanTime(OffsetDateTime scanTime) {
        this.scanTime = scanTime;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
