package com.yising.fast.cmd.pojo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "cmd")
@EntityListeners(AuditingEntityListener.class)
public class Cmd {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 命令名称
     * */
    @Column(name = "name")
    private String name;

    /**
     * 命令描述
     * */
    @Column(name = "description")
    private String description;

    /**
     * adb 不带参数的命令
     * */
    @Column(name = "short_cmd")
    private String shortCmd;

    /**
     * cmd命令，带参数占位符
     * */
    @Column(name = "cmd")
    private String cmd;

    /**
     * 命令类型，adb、hdc或者cmd
     * */
    @Column(name = "type")
    private String type;

    /**
     * 是否显示
     * */
    @Column(name = "is_show")
    private boolean isShow;

    /**
     * 权重
     * */
    @Column(name = "weight")
    private int weight;

    /**
     * 默认参数
     * */
    @Column(name = "default_param")
    private String defaultParam;
}
