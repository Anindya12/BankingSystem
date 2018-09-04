package com.nostratech.project.converter;

import com.nostratech.project.persistence.domain.Transfer;
import com.nostratech.project.persistence.vo.TransferVO;
import com.nostratech.project.util.ExtendedSpringBeanUtil;
import org.springframework.stereotype.Component;

@Component
public class TransferVOConverter extends BaseVoConverter<TransferVO, TransferVO, Transfer> implements IBaseVoConverter<TransferVO, TransferVO, Transfer>{

    @Override
    public TransferVO transferModelToVO(Transfer model, TransferVO vo) {
        if (null == vo) vo = new TransferVO();

        super.transferModelToVO(model, vo);
        ExtendedSpringBeanUtil.copySpecificProperties(model, vo,
                new String[]{"accountIdSource","accountIdDest","balance"}
        );

        return vo;
    }

    @Override
    public Transfer transferVOToModel(TransferVO vo, Transfer model) {
        if (null == model) model = new Transfer();

        ExtendedSpringBeanUtil.copySpecificProperties(vo, model,
                new String[]{"accountIdSource","accountIdDest","balance"});

        return model;
    }
}
