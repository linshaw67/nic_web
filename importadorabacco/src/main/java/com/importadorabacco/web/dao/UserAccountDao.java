package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by tanhengyi on 15-5-13.
 */
@Repository
public interface UserAccountDao {
    int insert(User user);

    int update(User user);

    User selectById(Long id);

    User selectByEmail(String email);
}
