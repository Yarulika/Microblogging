package com.sda.microblogging.repository;

import com.sda.microblogging.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findPostsByOwnerUserId(int userId);
    List<Post> findByOrderByCreationDate();
    int countByOriginalPostId(int postId);
    List<Post> findTop10ByOwnerIsPrivateOrderByCreationDateDesc(boolean isPrivate);
    List<Post> findPostsByOwnerUsernameAndOwnerIsPrivate(String username,boolean isPrivate);
    List<Post> findPostsByOwnerUsername(String username);

    @Query(value = "select * from posts p where p.owner_id in ((select f.user_id from followers f where f.follower_user_id=?1), ?2);",nativeQuery = true)
    List<Post> findUserPostAndFollowingUserPost(Integer userId, Integer usersId);
}
