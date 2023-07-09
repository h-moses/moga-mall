package com.ms.product.service.impl;

import com.ms.product.entity.UndoLog;
import com.ms.product.mapper.UndoLogMapper;
import com.ms.product.service.IUndoLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ms
 * @since 2023-07-01
 */
@Service
public class UndoLogServiceImpl extends ServiceImpl<UndoLogMapper, UndoLog> implements IUndoLogService {

}
