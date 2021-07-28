package generate;

import generate.Emails;

public interface EmailsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Emails record);

    int insertSelective(Emails record);

    Emails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Emails record);

    int updateByPrimaryKey(Emails record);
}