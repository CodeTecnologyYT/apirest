package com.nizum.apirest.model.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="NIM_ROL", schema = "PUBLIC")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolEntity {
    @Id
    @Column(name = "ROL_ID", nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ROL_NAME", nullable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ROL_DATE_CREATED", columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ROL_DATE_MODIFIED", columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date dateModified;
}
