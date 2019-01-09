package com.superman.superman.dao;

import com.superman.superman.Dto.UpdateWxOpenId;
import com.superman.superman.model.Oder;
import com.superman.superman.model.Userinfo;
import com.superman.superman.req.InvCode;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    int insert(Userinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    int insertSelective(Userinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    Userinfo selectByPrimaryKey(Long id);


    @Select("select * from userinfo where wxOpenId=#{id}")
    Userinfo queryUserWxOpenId(String id);

    @Update("update userInfo where wxOpenId=#{id},userName=#{name},userPhoto=#{photo}")
    Integer updateUserWxOpenId(UpdateWxOpenId uu);


    Userinfo selectByPhone(String userPhone);

    @Select("select SUM(promotion_amount) from oder where  p_id in (select pddPid from userinfo where  id in (select userId from agent where agentId=#{uid}))")
    Integer queryAllPidForAgentId(Integer uid);

    List<Userinfo> selectIn(@Param("list") List userlist);

    List<Userinfo> selectInFans(@Param("list") List userlist);

    //TODO sql待优化
    List<Userinfo> selectInUserInfo(@Param("list") List userlist);

    @Select("select id,userName,createTime,userPhone,roleId from userinfo where id in (SELECT userId FROM agent WHERE agentId= #{agentId} and status=0) and status=0 ORDER by roleId asc  limit #{star},#{end}")
    List<Userinfo> selectInUserInfoForAgentId(@Param("agentId") Long agentId, @Param("star") Integer star, @Param("end") Integer end);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Userinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table userinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Userinfo record);

    @Insert("insert into invcode(userId,createTime) values (#{id},now())")
    Integer insertCode(Long id);

    @Select("select ifnull(id,0) from invcode where userId=#{id}")
    Integer queryCodeId(Long id);

    @Select("select userId from invcode where id=#{id}")
    Integer queryUserCode(Long id);



}