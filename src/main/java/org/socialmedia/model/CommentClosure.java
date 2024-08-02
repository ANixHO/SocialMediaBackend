package org.socialmedia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comment_closure")
public class CommentClosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ancestor_id", nullable = false)
    private Comment ancestor;

    @ManyToOne
    @JoinColumn(name = "descendent_id", nullable = false)
    private Comment descendent;

    @Column(nullable = false)
    private int depth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getAncestor() {
        return ancestor;
    }

    public void setAncestor(Comment ancestor) {
        this.ancestor = ancestor;
    }

    public Comment getDescendent() {
        return descendent;
    }

    public void setDescendent(Comment descendent) {
        this.descendent = descendent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
