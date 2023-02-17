package com.blogapp.entities;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//ConstraintViolationException
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private int id;
	
	@Column(name = "user_name", nullable = false, length = 100)
	@NotBlank(message = "username cannot be null")
	private String name;
	
	@Column(name="email", nullable = false, length = 70)
	@Email(message = "incorrect email format")
	private String email;
	
	@Column(name="password", nullable = false, length = 100)
	@NotNull(message = "password cannot be null")
	private String password;
	
	@Column(name = "about", length = 500)
	private String about;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

//	referencedColumnName --> specify the id

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "id")
	)
	private Set<Role> roles = new HashSet<>();

	public User() {
		
	}

	public User(String name, String email, String password, String about) {
		super();
		
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


//	userDetails override methods
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		here we have to set the role
//		 we get the roles form roles field
		List<SimpleGrantedAuthority> grandAuth = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return grandAuth;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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


	public void setPassword(String password) {
		this.password = password;
	}



	public String getAbout() {
		return about;
	}



	public void setAbout(String about) {
		this.about = about;
	}
	
	
	
	
}
