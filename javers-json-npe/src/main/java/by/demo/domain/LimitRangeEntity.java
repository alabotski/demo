package by.demo.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.javers.core.metamodel.annotation.DiffIgnore;
import org.javers.core.metamodel.annotation.TypeName;
import org.javers.core.metamodel.annotation.ValueObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "limitation_range")
@TypeName("LimitRange")
//@ValueObject
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LimitRangeEntity extends BaseEntity {

  @Column(name = "caption", nullable = false)
  private String caption;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  @JoinColumn(name = "limit_id")
  @ToString.Exclude
  @DiffIgnore
  private LimitEntity limit;

  @OneToMany(mappedBy = "limitRange", orphanRemoval = true, cascade = CascadeType.ALL)
  @ToString.Exclude
  @Builder.Default
  private Set<LimitSignatureEntity> limitSignatures = new HashSet<>();
}
