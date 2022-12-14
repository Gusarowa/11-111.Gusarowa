package services;

import lombok.RequiredArgsConstructor;
import models.Comment;
import models.Post;
import repositories.CommentRepository;
import repositories.PostRepository;

import java.util.List;

@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post findPostByImageName(String imageName) {
        return postRepository.findByImageName(imageName).orElse(null);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void updatePost(Post post) {
        postRepository.update(post);
    }

    public void deletePostById(Long id) {
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }
}
