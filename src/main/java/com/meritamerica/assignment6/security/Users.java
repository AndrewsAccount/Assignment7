package com.meritamerica.assignment6.security;

//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.validation.constraints.NotEmpty;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.meritamerica.assignment6.models.AccountHolder;
//
//@Entity  // This class is "backing 
//public class Users {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
//
//	@Column(nullable = false, unique = true)
//	private String username;
//	@Column(nullable = false) 
//	private String password;
//	private boolean enabled;
//	private String role = "ROLE_USER ";
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private AccountHolder accountHolder;
//	@NotEmpty
//	private String authorities;
//	
//	public Users(String userName, String password, boolean enabled, String authorities) {
//		this.username = userName;
//		this.password = password;
//		this.enabled = enabled;
//		this.authorities = authorities;
//	}
//	
//	public Users() {
//		this.enabled = true;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUserName(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public boolean isEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public AccountHolder getAccountHolder() {
//		return accountHolder;
//	}
//
//	public void setAccountHolder(AccountHolder accountHolder) {
//		this.accountHolder = accountHolder;
//	}
//
//	public String getAuthorities() {
//		return authorities;
//	}
//
//	public void setAuthorities(String authorities) {
//		this.authorities = authorities;
//	}
//	
//	public List<GrantedAuthority> getAuthorityList() {
//		if (this.authorities.length() > 0) {
//			String[] arrStr = this.authorities.split(",");
//			List<GrantedAuthority> authorityList = new ArrayList<>();
//			for (String auth : arrStr) {
//				authorityList.add(new Authority(auth));
//			}
//			return authorityList;
//		}
//		return new ArrayList<GrantedAuthority>();
//	}
//	
//	
//
//}
