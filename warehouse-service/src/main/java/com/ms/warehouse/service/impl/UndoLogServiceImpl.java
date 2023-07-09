package com.ms.warehouse.service.impl;

import com.ms.warehouse.entity.UndoLog;
import com.ms.warehouse.mapper.UndoLogMapper;
import com.ms.warehouse.service.IUndoLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-09
 */
@Service
public class UndoLogServiceImpl extends ServiceImpl<UndoLogMapper, UndoLog> implements IUndoLogService {

}
