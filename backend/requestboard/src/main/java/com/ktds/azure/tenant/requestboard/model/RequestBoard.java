package com.ktds.azure.tenant.requestboard.model;


import com.ktds.azure.tenant.requestboard.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_REQUEST_BOARD")
public class RequestBoard extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3724542338720346254L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String writerEmail;

    @Column
    private String projectCode;

    @Column(nullable = false, length = 500)
    private String projectName;

    @Column(length = 1000)
    private String purpose;

    @Column
    private Boolean requiredDev;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String budgetManager;

    @Column
    private String budgetManagerEmail;

    @Column
    private String operationManager;

    @Column
    private String operationManagerEmail;

    @Column
    private Long budget;

    @Column
    private Boolean alert;

    @Column
    private Long alertBudget;

    @Column
    private String managementGroup;

    @Column
    private Long ipCount;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestBoardType type;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestBoardState state;

    @Column(length = 500)
    private String budgetLink;

    @Column(length = 4000)
    private String request;

    @Column
    private LocalDateTime requestDate;

    @Column
    private LocalDateTime denyDate;

    @Column
    private LocalDateTime approvedDate;

    @Column
    private LocalDateTime completeDate;

    @Column(length = 4000)
    private String denyReason;

    @Column(length = 4000)
    private String opinion;

    @Override
    public String toString() {
        return "RequestBoard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", writerEmail='" + writerEmail + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", purpose='" + purpose + '\'' +
                ", requiredDev=" + requiredDev +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgetManager='" + budgetManager + '\'' +
                ", budgetManagerEmail='" + budgetManagerEmail + '\'' +
                ", operationManager='" + operationManager + '\'' +
                ", operationManagerEmail='" + operationManagerEmail + '\'' +
                ", budget=" + budget +
                ", alert=" + alert +
                ", alertBudget=" + alertBudget +
                ", managementGroup='" + managementGroup + '\'' +
                ", ipCount=" + ipCount +
                ", type=" + type +
                ", state=" + state +
                ", budgetLink='" + budgetLink + '\'' +
                ", request='" + request + '\'' +
                ", requestDate=" + requestDate +
                ", denyDate=" + denyDate +
                ", approvedDate=" + approvedDate +
                ", completeDate=" + completeDate +
                ", denyReason=" + denyReason +
                ", opinion=" + opinion +
                '}';
    }
}
