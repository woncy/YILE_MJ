start java  -server  -verbose:gc   -cp ./client majiang.client.ClientMain > log/ClientMain.log 
start java   -server  -verbose:gc   -cp ./manager majiang.client.ManagerMain > log/ManagerMain.log 
start java   -server  -verbose:gc   -cp ./boss game.boss.BossMain > log/BossMain.log 
start java   -server  -verbose:gc   -cp ./scene game.scene.SceneMain > log/SceneMain.log 
start java   -server  -verbose:gc   -cp ./gateway game.gateway.GatewayMain > log/GatewayMain.log

