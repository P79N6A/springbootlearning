package mybatistest.dao;

import mybatistest.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StudentDAO {
    Student selectById(long id);
}
