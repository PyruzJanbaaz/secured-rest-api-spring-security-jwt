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
@Table(name="menu")
@Where(clause="is_deleted= false")
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity<Long> {

    @Size(max=200)
    public String title;

    @Size(max=40)
    public String role;

    public Integer sort;

    public Long parentId;

    @Size(max=1500)
    public String urlAddress;

    @Size(max = 50)
    public String icon;

    @Where(clause = "is_deleted=false")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "access_menu",
            joinColumns = {@JoinColumn(name = "menu_id")},
            inverseJoinColumns = {@JoinColumn(name = "access_id")}
    )
    private List<Access> accesses;
}
