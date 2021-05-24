package com.nizum.apirest.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "NIM_USER", schema = "PUBLIC")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity {

    @Id
    @Column(name = "USR_ID", nullable = false, columnDefinition = "VARCHAR2(36)")
    private String id;
    @Column(name = "USR_NAME", nullable = false, columnDefinition = "VARCHAR2(120)")
    private String name;
    @Column(name = "USR_EMAIL", nullable = false, unique = true, columnDefinition = "VARCHAR2(120)")
    private String email;
    @Column(name = "USR_PASSWORD", nullable = false, columnDefinition = "VARCHAR2(120)")
    private String password;
    @Column(name = "USR_STATUS", nullable = false ,columnDefinition = "TINYINT")
    private Integer status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USR_DATE_CREATED", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date dateCreate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USR_DATE_MODIFIED", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private Date dateModified;
    @Column(name = "USR_LAST_LOGIN")
    private Date dateLastLogin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRef")
    private List<PhoneEntity> phones;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "NIM_USER_AND_ROLE",
            joinColumns = @JoinColumn(name = "UIR_USR_ID", columnDefinition = "VARCHAR(36)"),
            inverseJoinColumns = @JoinColumn(name = "UIR_ROL_ID", columnDefinition = "VARCHAR(36)")

    )
    private Set<RolEntity> roles;

}
