package com.example.demo.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "tu_user")
@Getter @Setter
@ToString
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

  private static final long serialVersionUID = 7535937185214543104L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(length = 50)
  private String id;

  private String username;

  private String password;

  @ElementCollection(targetClass = Authority.class)
  @JoinTable(name = "tu_user_authorities", joinColumns = {@JoinColumn(name = "user_id")})
  @Column(name = "authority", nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<Authority> authorities = new HashSet<>();

  @ManyToOne
  private Company company;

  public Optional<SamlSetting> getSamlSetting(Idp idp) {
    return getCompany().getSamlSetting(idp);
  }

  public String getCompanyDomainName() {
    return this.company.getDomainName();
  }
}
