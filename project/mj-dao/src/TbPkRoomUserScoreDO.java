package  game.boss.poker.dao.entity;

import java.beans.Transient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import org.forkjoin.core.dao.UniqueInfo;


import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

import  org.forkjoin.core.dao.EntityObject;
import  org.forkjoin.core.dao.KeyObject;
import  org.forkjoin.core.dao.TableInfo;

public class TbPkRoomUserScoreDO extends EntityObject<TbPkRoomUserScoreDO, TbPkRoomUserScoreDO.Key>{

	/**扑克房间ID*/
	private int roomId;
	/**玩家ID*/
	private Integer userId;
	/**房间chech_id*/
	private Integer roomCheckId;
	/**分数*/
	private Integer score;
	/**扑克牌ID*/
	private Integer pokerId;
	/**局数*/
	private Integer chapterId;

	public static class Key implements KeyObject<TbPkRoomUserScoreDO, TbPkRoomUserScoreDO.Key>{

		public Key() {
   		}

		@JsonIgnore
		@Transient
		@Override
		public Object[] getQueryParams() {
			return new Object[]{
			};
		}

        @Override
        public String toStringKey(){
