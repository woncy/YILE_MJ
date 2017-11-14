package game.scene.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库操作相关
 *
 * @author zuoge85@gmail.com on 2016/12/28.
 */
@Service
public class DbService {
    @Autowired
    private AsyncDbService asyncDbService;

   
}
