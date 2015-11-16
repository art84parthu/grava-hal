package com.bol.assignment.grava.hal.dao.impl;

import com.bol.assignment.grava.hal.dao.PlayerDao;
import com.bol.assignment.grava.hal.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    private static int id = 100;
    private NamedParameterJdbcTemplate template;
    private RowMapper<Player> userMapper = (rs, rowNum) -> {
        Player u = new Player();

        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));

        return u;
    };

    @Autowired
    public PlayerDaoImpl(DataSource ds) {
        template = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public Player getPlayerByName(String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);

        String sql = "SELECT * FROM user WHERE username=:name";

        List<Player> list = template.query(
                sql,
                params,
                userMapper);

        if (list != null && !list.isEmpty()) return list.get(0);

        return null;
    }

    @Override
    public Player getPlayerById(int id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM user WHERE id=:id";

        List<Player> list = template.query(
                sql,
                params,
                userMapper);

        if (list != null && !list.isEmpty()) return list.get(0);

        return null;
    }

    @Override
    public int addNewPlayer(Player user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id++);
        params.put("name", user.getName());

        String sql = "insert into player_info (id, name) values (:id, :name);";

        template.update(sql, params);
        return (id - 1);
    }
}
