package com.flaviopessini.springbootapigateway.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table(name = "books")
@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author", nullable = false, length = 80)
    private String author;

    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date launchDate;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
