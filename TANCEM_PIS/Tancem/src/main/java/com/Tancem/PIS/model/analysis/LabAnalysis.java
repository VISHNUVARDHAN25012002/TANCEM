package com.Tancem.PIS.model.analysis;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "lab_analysis")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LabAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lab_analysis_id_seq")
    @SequenceGenerator(name = "lab_analysis_id_seq", sequenceName = "lab_analysis_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", referencedColumnName = "id", nullable = false)
    private Analysis analysis;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

   }
