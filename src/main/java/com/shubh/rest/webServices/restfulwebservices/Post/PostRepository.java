package com.shubh.rest.webServices.restfulwebservices.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from post where user_id = :id" ,nativeQuery = true)
    void deletePosts(@Param("id") int id);

}
