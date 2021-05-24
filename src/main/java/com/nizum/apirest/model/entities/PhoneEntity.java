package com.nizum.apirest.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "NIM_PHONE", schema = "PUBLIC")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneEntity {
    @Id
    @Column(name="PHE_ID", nullable = false ,columnDefinition = "VARCHAR2(36)" )
    private String id;
    @Column(name="PHE_NUMBER", nullable = false, columnDefinition = "VARCHAR2(15)")
    private String number;
    @Column(name="PHE_CITY_CODE", nullable = false, columnDefinition = "VARCHAR(1)")
    private String cityCode;
    @Column(name="PHE_COUNTRY_CODE", nullable = false, columnDefinition = "VARCHAR2(12)")
    private String countryCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PHE_DATE_CREATED", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dateCreate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PHE_DATE_MODIFIED", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date dateModified;
    @ManyToOne
    @JoinColumn(name = "PHE_USR_ID", nullable = false, updatable = false)
    private UserEntity userRef;
}
