package cn.stylefeng.guns.modular.system.dao;

import cn.stylefeng.guns.modular.system.model.Item;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-03
 */
public interface ItemMapper extends BaseMapper<Item> {
	List<Item> selectByClassCode(@Param("classcode")  String classcode);
}
