package com.pyruz.rest.secured.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "access")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
@AllArgsConstructor
public class Access extends BaseEntity<Long> {

    @Size(max = 50)
    private String title;

    @Where(clause = "is_deleted=false")
    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_access",
            joinColumns = {@JoinColumn(name = "access_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    @Where(clause = "is_deleted=false")
    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_menu",
            joinColumns = {@JoinColumn(name = "access_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")}
    )
    private List<Menu> menus;
}
