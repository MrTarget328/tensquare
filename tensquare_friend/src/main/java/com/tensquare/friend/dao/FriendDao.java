package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    public Friend  findByFriendidAndUserid(String friend,String userid);

    @Modifying
    @Query(value = "UPDATE tb_friend set islike =?1 where userid = ?2 and friendid = ?3",nativeQuery = true)
    public void updateIsLike(String isLike,String userId,String friendId);

    @Modifying
    @Query(value = "delete from tb_friend where userid = ? and friendid = ?",nativeQuery = true)
    void deleteFriend(String userId, String friendid);
}
