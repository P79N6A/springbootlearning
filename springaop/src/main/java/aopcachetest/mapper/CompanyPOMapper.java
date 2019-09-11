package aopcachetest.mapper;

import aopcachetest.dto.CompanyDTO;
import aopcachetest.entity.CompanyPO;
import aopcachetest.entity.CompanyPOExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyPOMapper {
    CompanyDTO selectByCompanyPO(@Param("companyPO") CompanyPO companyPO);


    long countByExample(CompanyPOExample example);

    int deleteByExample(CompanyPOExample example);

    @Delete({
        "delete from company",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into company (user_id, company_name)",
        "values (#{userId,jdbcType=BIGINT}, #{companyName,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(CompanyPO record);

    int insertSelective(CompanyPO record);

    List<CompanyPO> selectByExampleWithRowbounds(CompanyPOExample example, RowBounds rowBounds);

    List<CompanyPO> selectByExample(CompanyPOExample example);

    @Select({
        "select",
        "id, user_id, company_name",
        "from company",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("CompanyPOMapper.BaseResultMap")
    CompanyPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CompanyPO record, @Param("example") CompanyPOExample example);

    int updateByExample(@Param("record") CompanyPO record, @Param("example") CompanyPOExample example);

    int updateByPrimaryKeySelective(CompanyPO record);

    @Update({
        "update company",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "company_name = #{companyName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CompanyPO record);
}