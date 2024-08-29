package ewm.stats_service;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;

@Data
    @Entity
    @Table(name = "statistic")
    public class Hit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @Column(name = "uri", nullable = false)
        String uri;
        @Column(name = "app", nullable = false)
        String app;
        @Column(name = "ip", nullable = false)
        String ip;
        @Column(name = "timestamp", nullable = false)
        Timestamp timestamp;

    }
