package com.flaviopessini.springbootapigateway.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "permission")
@Getter
@Setter
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public String getAuthority() {
        return this.getDescription();
    }
}
