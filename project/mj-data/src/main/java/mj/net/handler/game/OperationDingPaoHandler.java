package mj.net.handler.game;

import mj.net.handler.MessageHandler;
import mj.net.message.game.OperationDingPao;

/**
 * @Package:mj.net.handler.game
 * @ClassName: OperationDingPaoHandler
 * @Description: TODO
 * @author XuKaituo
 * @date May 17, 2017  2:55:57 PM
 */
public interface OperationDingPaoHandler<U> extends MessageHandler<OperationDingPao, U> {
    @Override
    boolean handler(OperationDingPao msg, U user);
}
