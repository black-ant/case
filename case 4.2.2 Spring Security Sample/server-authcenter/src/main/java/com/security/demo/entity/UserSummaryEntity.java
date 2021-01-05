package com.security.demo.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/1/17 22:21
 * @Version 1.0
 **/

@Data
@Entity(name="user_summary")
public class UserSummaryEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long sn;
    private int userid;
    private String username;
    private String password;
    private String userfrom;
    private String userstatus;
    @ManyToMany(targetEntity = UserRolesEntity.class, fetch = FetchType.EAGER)
    private List<UserRolesEntity> userroles;
    @Transient
    private Set<GrantedAuthority> authorities = new HashSet<>();


    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRolesEntity role : this.userroles) {
            for (AuthorityEntity authority : role.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority.getValue()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}