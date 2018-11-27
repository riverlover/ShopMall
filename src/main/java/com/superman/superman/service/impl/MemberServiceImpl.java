package com.superman.superman.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.superman.superman.Dto.TeamList;
import com.superman.superman.dao.AgentDao;
import com.superman.superman.dao.OderMapper;
import com.superman.superman.dao.PddDao;
import com.superman.superman.dao.UserinfoMapper;
import com.superman.superman.model.Agent;
import com.superman.superman.model.Role;
import com.superman.superman.model.Test;
import com.superman.superman.model.Userinfo;
import com.superman.superman.service.MemberService;
import com.superman.superman.service.OderService;
import com.superman.superman.service.RoleService;
import com.superman.superman.service.UserApiService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.var;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujupeng on 2018/11/8.
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
    private final static Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);


    @Autowired
    private AgentDao agentDao;
    @Autowired
    private OderService oderService;
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private UserApiService userApiService;

    @Override
    public String aaaa() {
        return null;
    }

    @Override
    public String bbb() {
        return null;
    }

    @Override
    public String ccc() {
        Long uid = 0l;
        var t1 = new Test();

        t1.setAgentId(uid.intValue());
//        pddDao.selectRchid(t1);
        return null;
    }

    @Override
    public Integer getMyMoney(@NonNull Long uid) {
        var role = userinfoMapper.selectByPrimaryKey(uid);
        var roleId = role.getRoleId();
        var pddPid = role.getPddpid();
        var listId = new ArrayList<>();

        switch (roleId) {
            case 1:
                //总代
                var tt = oderService.countPddOderForId(pddPid);

                break;
            //代理
            case 2:
                //查询我的订单收入
                var temp = oderService.countPddOderForId(pddPid);
                var meIncome = temp == null ? 0 : temp;

                //查询我的下级粉丝
                var agent = agentDao.queryForAgentId(uid.intValue());
                if (agent == null) {
                    return meIncome;
                }
                //遍历出UID
                for (Agent ag : agent) {
                    var userId = ag.getUserId();
                    listId.add(userId);
                }
                //查询出粉丝的PID集合
                List<String> userinfos = userinfoMapper.selectIn(listId);
                //如果粉丝没有贡献
                if (userinfos==null){
                    return meIncome;
                }
                //查询出粉丝贡献的订单收入
                var fans = oderService.countPddOderForIdList(userinfos);
                logger.warn("粉丝收入==" + fans);
                if (fans == null) {
                    return meIncome;
                }
                logger.warn("代理收入==" + meIncome);

                return meIncome + fans;
            //粉丝
            case 3:
        }
        return null;
    }

    @Override
    public JSONObject getMyTeam(Long uid) {
        return null;
    }

//
//    @Override
//    public JSONObject getMyTeam(Long uid) {
//        //团队成员数量
//        Integer teamSum=0;
//        Integer agentSum=0;
//        Test t1=new Test();
//        t1.setAgentId(uid.intValue());
//        //
//
//        //直推会员Id列表
//        List uidList=new ArrayList();
//        List<TeamList> teamLists=new ArrayList();
//        JSONObject jsonObject = new JSONObject();
//        JSONArray json = new JSONArray();
//        JSONArray json1 = new JSONArray();
//        List<Test> test = pddDao.selectRchid(t1);
//        if (test!=null){
//            for (Test tt:test){
//                uidList.add(tt.getUserId());
//            }
//            List<Role> roles = pddDao.selectRchidlist(uidList);
//            //会员下级Id
//            List<Integer> uidLiLatp=new ArrayList();
//
//            for (Role tt:roles){
//                Long tId = tt.getUserId();
//                TeamList t=new TeamList();
//                t.setUserId(tt.getUserId());
//                t.setScore(tt.getScore());
//                if (tt.getScore()==0)
//                {
//                    t.setMember(0);
//                    t.setDaili(false);
//                }
//                if (tt.getScore()<100&&tt.getScore()!=0)
//                {
//                    t.setDaili(true);
//                    uidLiLatp.add(tId.intValue());
//                    Integer agSum = pddDao.countAgentId(tId.intValue());
//                    t.setMember(agSum);
//                }
//                teamLists.add(t);
//            }
//            List lowList=new ArrayList();
//            List<Test> vipNolist = pddDao.selectTchidlist(uidLiLatp);
//            for (Test tt:vipNolist){
//                lowList.add(tt.getUserId());
//            }
//            List<Role> roleList = pddDao.selectRchidlist(lowList);
//            teamSum=lowList.size()+teamLists.size();
//            agentSum=uidLiLatp.size();
//            for (TeamList a:teamLists)
//            {
//                    JSONObject jo = new JSONObject();
//                    jo.put("id", a.getUserId());
//                    jo.put("daili", a.getDaili());
//                    jo.put("member", a.getMember());
//                    jo.put("score", a.getScore());
//                    json.add(jo);
//            }
//            for (Role a:roleList)
//            {
//                JSONObject jo = new JSONObject();
//                jo.put("id", a.getUserId());
//                jo.put("score", a.getScore());
//                json1.add(jo);
//            }
//            jsonObject.put("zhishu", json);
//            jsonObject.put("xiaji", json1);
//            jsonObject.put("agentSum", agentSum);
//            jsonObject.put("teamSum", teamSum);
//            return jsonObject;
//
//        }
//        jsonObject.put("zhishu", null);
//        jsonObject.put("xiaji", null);
//        jsonObject.put("agentSum", 0);
//        jsonObject.put("teamSum", 0);
//        return jsonObject;
//    }


}
