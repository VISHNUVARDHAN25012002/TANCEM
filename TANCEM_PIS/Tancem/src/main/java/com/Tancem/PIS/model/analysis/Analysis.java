
package com.Tancem.PIS.model.analysis;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "analysis")
@EntityListeners(AuditingEntityListener.class)
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analysis_id_seq")
    @SequenceGenerator(name = "analysis_id_seq", sequenceName = "analysis_id_seq", allocationSize = 1)
    private int id;

    private String analysisType;
    private String description;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Default to true

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
