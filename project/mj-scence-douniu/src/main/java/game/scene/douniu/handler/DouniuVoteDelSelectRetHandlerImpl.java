package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;

import org.springframework.stereotype.Component;
import mj.net.handler.game.douniu.DouniuVoteDelSelectRetHandler;
import mj.net.message.game.douniu.DouniuVoteDelSelectRet;

/**
 * @author zuoge85@gmail.com on 2017/1/18.
 */
@Component
public class DouniuVoteDelSelectRetHandlerImpl  implements DouniuVoteDelSelectRetHandler<DouniuSceneUser> {
    @Override
    public boolean handler(DouniuVoteDelSelectRet msg, DouniuSceneUser user) {
        user.getRoom().voteDelSelect(msg, user);
        return false;
    }


}