package services;

import models.Comment;
import repositories.CommentsRepository;

import java.util.List;

public class CommentService {
    private final CommentsRepository commentsRepository = new CommentsRepository();

    public void saveComment(Comment comment) {
        commentsRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentsRepository.findAll();
    }

    public void deleteComment(Long id) {
        commentsRepository.delete(id);
    }
}
