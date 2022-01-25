package by.demo.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.javers.core.metamodel.annotation.TypeName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "limitation")
@TypeName("Limit")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LimitEntity extends BaseEntity {

  @Column(name = "caption", nullable = false)
  private String caption;

  @OneToMany(mappedBy = "limit", orphanRemoval = true, cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  private Set<LimitRangeEntity> limitRanges = new HashSet<>();

}
