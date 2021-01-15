package com.shopify_image_repository.image_repos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="images")
public class Image
    extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imgid;

    @ManyToOne
    @JoinColumn(name="userid",
            nullable = false)
    @JsonIgnoreProperties("images")
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Boolean isprivate;

    /**
     * Default constructor used primarily by the JPA.
     */
    public Image() {
    }


}
