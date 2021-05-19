package io.seata.samples.integration.storage.service;

import io.seata.samples.integration.common.dto.CommodityDTO;
import io.seata.samples.integration.common.enums.RspStatusEnum;
import io.seata.samples.integration.common.response.ObjectResponse;
import io.seata.samples.integration.storage.entity.TStorage;
import io.seata.samples.integration.storage.mapper.TStorageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存服务实现类
 * </p>
 * <p>
 * * @author lidong
 *
 * @since 2019-09-04
 */
@Service
public class TStorageServiceImpl implements ITStorageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TStorageDao tStorageDao;


    @Override
    public ObjectResponse decreaseStorage(CommodityDTO commodityDTO) {
        ObjectResponse<Object> response = new ObjectResponse<>();

        try {
            TStorage storage = tStorageDao.getOne(commodityDTO.getId());
            storage.setCount(storage.getCount() - commodityDTO.getCount());
            tStorageDao.save(storage);

            response.setStatus(RspStatusEnum.SUCCESS.getCode());
            response.setMessage(RspStatusEnum.SUCCESS.getMessage());
            return response;
        } catch (Exception E) {

            response.setStatus(RspStatusEnum.FAIL.getCode());
            response.setMessage(RspStatusEnum.FAIL.getMessage());
            return response;
        }

    }
}
