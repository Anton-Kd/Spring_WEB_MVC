package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final AtomicLong postId;
    private final Map<Long, Post> posts;

    public PostRepositoryImpl() {
        postId = new AtomicLong(0);
        posts = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        long postCurrentId = post.getId();
        if (postCurrentId > 0 && posts.containsKey(postCurrentId)) {
            posts.replace(postCurrentId, post);
        } else {
            long newPostId = postCurrentId == 0 ? postId.incrementAndGet() : postCurrentId;
            post.setId(newPostId);
            posts.put(newPostId, post);
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}