package com.importadorabacco.web.dao;

import com.importadorabacco.web.model.ResetToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by tanhengyi on 15-8-2.
 */
@Repository
public interface UserAccountResetDao {
    void insert(ResetToken token);

    void updateStatus(@Param("token") String token, @Param("status") Integer status);

    ResetToken selectLastUnusedToken(String email);

    ResetToken selectByToken(String token);
}
