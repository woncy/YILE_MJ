package game.boss.services;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.service.AbstractAsyncService;
import game.boss.model.User;
import mj.net.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 异步服务，请注意线程安全
 *
 * @author zuoge85 on 14-1-8.
 */
public final class AsyncService extends AbstractAsyncService {
    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    public AsyncService(int threadNums) {
        super(threadNums, threadNums);
    }

    @SuppressWarnings("unchecked")
    public void excuete(MessageHandler handler, Message message, User user) {
        submit(user.getUserId(), () -> handler.handler(message, user));
    }

    public void excuete(Runnable run) {
        submit(run);
    }
}
