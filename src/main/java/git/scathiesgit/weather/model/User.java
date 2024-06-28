package git.scathiesgit.weather.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = "locations")
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "[a-zA-Z][a-zA-Z_\\-0-9]+",
            message = "должно начинаться с буквы и может содержать знаки '-' '_' и цифры ")
    @Size(min = 5, message = "не менее 5 символов")
    private String username;

    @Column(nullable = false)
    @Size(min = 5, message = "не менее 5 символов")
    private String password;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Location> locations = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
