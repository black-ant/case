package com.gang.study.mapstruct.demo;

import com.gang.study.mapstruct.demo.to.PersonDO;
import com.gang.study.mapstruct.demo.to.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Classname PersonConverter
 * @Description TODO
 * @Date 2020/8/22 15:41
 * @Created by zengzg
 */
@Mapper
public interface PersonConverter {

    PersonConverter INSTANCE = Mappers.getMapper(PersonConverter.class);

    @Mappings(@Mapping(source = "name", target = "userName"))
    PersonDTO do2dto(PersonDO person);
}
