package com.nostratech.project.converter;

import com.nostratech.project.persistence.domain.Transaction;
import com.nostratech.project.persistence.vo.TransactionVO;
import com.nostratech.project.util.ExtendedSpringBeanUtil;
import org.springframework.stereotype.Component;

@Component
public class TransactionVOConverter extends BaseVoConverter<TransactionVO, TransactionVO, Transaction> implements IBaseVoConverter<TransactionVO, TransactionVO, Transaction>{

    @Override
    public TransactionVO transferModelToVO(Transaction model, TransactionVO vo) {
        if (null == vo) vo = new TransactionVO();

        super.transferModelToVO(model, vo);
        ExtendedSpringBeanUtil.copySpecificProperties(model, vo,
                new String[]{"accountId","balance","status"}
        );

        return vo;
    }

    @Override
    public Transaction transferVOToModel(TransactionVO vo, Transaction model) {
        if (null == model) model = new Transaction();

        ExtendedSpringBeanUtil.copySpecificProperties(vo, model,
                new String[]{"accountId","balance","status"});

        return model;
    }
}
