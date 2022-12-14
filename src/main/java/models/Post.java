package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
    private Long id;
    private String title;
    private String text;
    private byte[] imageData;
    private String imageName;
    private Long userId;
}
