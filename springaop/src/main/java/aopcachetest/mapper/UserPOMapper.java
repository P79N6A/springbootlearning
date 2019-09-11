package aopcachetest.mapper;

import aopcachetest.entity.UserPO;
import aopcachetest.entity.UserPOExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserPOMapper {
    long countByExample(UserPOExample example);

    int deleteByExample(UserPOExample example);

    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user (name, age)",
        "values (#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserPO record);

    int insertSelective(UserPO record);

    List<UserPO> selectByExampleWithRowbounds(UserPOExample example, RowBounds rowBounds);

    List<UserPO> selectByExample(UserPOExample example);

    @Select({
        "select",
        "id, name, age",
        "from user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserPO selectByPrimaryKey(Long id);


    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserPOExample example);

    int updateByPrimaryKeySelective(UserPO record);

    @Update({
        "update user",
        "set name = #{name,jdbcType=VARCHAR},",
          "age = #{age,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(@Param("record") UserPO record);


    //自定义
    List<UserPO> selectByPO(@Param("userPO") UserPO userPO);

    int insertByPO(@Param("userPO") UserPO userPO);
}