package com.pyruz.rest.secured.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "users")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long> {

    @Size(max = 50)
    private String username;
    @Size(max = 80)
    private String password;
    @Size(max = 250)
    private String email;
    @Size(max = 50)
    private String firstName;
    @Size(max = 50)
    private String lastName;

    @Where(clause = "is_deleted=false")
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_access",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "access_id")}
    )
    private List<Access> accesses;

}
