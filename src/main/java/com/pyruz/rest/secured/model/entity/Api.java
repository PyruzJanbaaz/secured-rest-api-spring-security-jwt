package com.pyruz.rest.secured.model.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "api")
@Where(clause = "is_deleted= false")
@NoArgsConstructor
@AllArgsConstructor
public class Api extends BaseEntity<Integer> {

    @Size(max = 200)
    public String title;

    @Size(max = 10)
    public String method;

    @Size(max = 40)
    public String role;

    @Size(max = 1500)
    public String url;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_api",
            joinColumns = {@JoinColumn(name = "api_id")},
            inverseJoinColumns = {@JoinColumn(name = "access_id")}
    )
    private List<Access> accesses;
}
