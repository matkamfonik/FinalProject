package pl.coderslab.finalproject;
import java.util.Collection;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import pl.coderslab.finalproject.entities.User;

@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private final User user;
	public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       User user) {
            super(username, password, authorities);
            this.user = user;
	}
}