package services;

import models.Post;
import repositories.PostsRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private static final PostsRepository repository = new PostsRepository();

    public void savePost(Post post) {
        repository.save(post);
    }

    public List<Post> getAllPosts() {
        return repository.findAll();
    }

    public Post findPost(Long id) {
        Optional<Post> post = repository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new IllegalArgumentException("No such post");
        }
    }

    public void deletePost(Long id) {
        repository.delete(id);
    }

    public void updatePost(Post post) {
        repository.update(post);
    }
}
