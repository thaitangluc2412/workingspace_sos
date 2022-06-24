package com.cnpm.workingspace.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Integer reviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "rating")
    private String rating;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    public Review(Customer customer, Property property, String rating,String content, LocalDateTime createDate) {
        this.customer = customer;
        this.property = property;
        this.rating = rating;
        this.content = content;
        this.createDate = createDate;
    }
}