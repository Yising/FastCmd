package com.yising.fast.cmd.repository;

import com.yising.fast.cmd.pojo.entity.Cmd;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 命令仓库
 *
 * @author yising
 */
public interface CmdRepository extends JpaRepository<Cmd, Long> {
}
