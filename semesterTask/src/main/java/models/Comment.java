package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
    private Long id;
    private String text;
    private Long userID;
    private Long postID;
}
