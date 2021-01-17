package com.shopify_image_repository.image_repos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image
    extends Auditable
{
    //#region fields / constructors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long imageid;

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
    private Boolean isprivate = false;

    /**
     * Default constructor used primarily by the JPA.
     */
    public Image() {
    }

    public Image( String title, String url, Boolean isprivate) {
        this.title = title;
        this.url =  url;
        this.isprivate = isprivate;
    }
    //#endregion
    //#region getters / setters
    public long getImageId() {
        return imageid;
    }

    public void setImageId(long imgId) {
        this.imageid = imgId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsPrivate() {
        return isprivate;
    }

    public void setIsPrivate(Boolean isprivate) {
        this.isprivate = isprivate;
    }

    //#endregion
}
