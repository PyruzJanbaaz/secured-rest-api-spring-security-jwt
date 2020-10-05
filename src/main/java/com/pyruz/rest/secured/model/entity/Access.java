package com.pyruz.rest.secured.model.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "access")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class Access extends BaseEntity<Long> {

    @Size(max = 50)
    private String title;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_access",
            joinColumns = {@JoinColumn(name = "access_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_api",
            joinColumns = {@JoinColumn(name = "access_id")},
            inverseJoinColumns = {@JoinColumn(name = "api_id")}
    )
    private List<Api> apis;
}
