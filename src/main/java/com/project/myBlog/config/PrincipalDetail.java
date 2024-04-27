package com.project.myBlog.config;

import com.project.myBlog.entity.User;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class PrincipalDetail  implements UserDetails, OAuth2User {


    private User user;
    private List<GrantedAuthority> authorities;
    private Map<String, Object> oauthUserAttributes;

    public PrincipalDetail(User user, List<GrantedAuthority> authorities, Map<String, Object> oauthUserAttributes) {
        this.user = user;
        this.authorities = authorities;
        this.oauthUserAttributes = oauthUserAttributes;
    }

    public static PrincipalDetail create(User user, Map<String, Object> oauthUserAttributes){
        return new PrincipalDetail(user,List.of(()->"ROLE_USER"), oauthUserAttributes);
    }

    public static PrincipalDetail create(User user){
        return new PrincipalDetail(user,List.of(()->"ROLE_USER"), new HashMap<>());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(oauthUserAttributes);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <A> A getAttribute(String name) {
        return (A) oauthUserAttributes.get(name);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{return "ROLE_"+user.getRoleType();});
        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    @Override
    public String getName() {
        return user.getEmail();
    }
}
