package org.socialmedia.dto;

import org.bson.types.Binary;

public class PostImageDTO {
    private String id;
    private Binary imageBinary;

    public PostImageDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Binary getImageBinary() {
        return imageBinary;
    }

    public void setImageBinary(Binary imageBinary) {
        this.imageBinary = imageBinary;
    }
}
