package com.rahman.bettary_app.network.model

import com.rahman.bettary_app.domain.model.BatteryModel
import com.rahman.educationinfo.domain.util.DomainMapper

class BatteryDtoMapper : DomainMapper<BatteryDto,BatteryModel> {
    override fun mapToDomainModel(model: BatteryDto): BatteryModel {
       return BatteryModel(
           name = model.name?:"",
           code = model.code?:""
       )
    }

    override fun mapFromDomainModel(domainModel: BatteryModel): BatteryDto {
      return BatteryDto(
          name = domainModel.name,
          code = domainModel.code
      )
    }

    fun toDomainList(items: List<BatteryDto>): List<BatteryModel>{
        return items.map { mapToDomainModel(it) };
    }
    fun fromDomainList(items: List<BatteryModel>): List<BatteryDto>{
        return items.map { mapFromDomainModel(it) };
    }


}