package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    /**
     * 添加好友
     * @param userId
     * @param friendid
     * @return
     */
    public int like(String userId, String friendid) {
        //查询此用户有没有添加过该好友
        Friend friend = friendDao.findByFriendidAndUserid(friendid, userId);
        if (friend!=null){
            return 0;
        }
        //添加单向好友
        friend = new Friend();
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friend.setUserid(userId);
        friendDao.save(friend);
        //添加双向好友  互相喜欢
        if (friendDao.findByFriendidAndUserid(userId, friendid)!=null){
            friendDao.updateIsLike("1",userId,friendid);
            friendDao.updateIsLike("1",friendid,userId);
        }
        return 1;
    }

    /**
     * 添加非好友
     * @param userId
     * @param friendid
     * @return
     */
    public int noLike(String userId, String friendid) {

        //查询此用户有没有添加过该好友
        NoFriend nofriend = noFriendDao.findByFriendidAndUserid(friendid, userId);
        if (nofriend!=null){
            return 0;
        }
        nofriend = new NoFriend();
        nofriend.setFriendid(friendid);
        nofriend.setUserid(userId);
        noFriendDao.save(nofriend);
        return 1;
    }

    /**
     * 删除好友
     * @param userId
     * @param friendid
     */
    public void deleteFriend(String userId, String friendid) {
        //删除好友表中 userID到friendId的数据
        friendDao.deleteFriend(userId,friendid);
        //更新friendid到userid的isLike 为 0
        friendDao.updateIsLike("0",friendid,userId);
        //向非好友表中添加一条数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
