package com.nian.wan.app.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class FenBean {


    /**
     * all : 23
     * group : [{"counts":"10","type_name":"角色扮演","type_id":"2","game_name":"边境之旅","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-19/5a619148b7119.png","create_time":"1516332795"},{"counts":"3","type_name":"策略","type_id":"1","game_name":"保卫萝卜","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-19/5a616c3da6317.png","create_time":"1516332780"},{"counts":"2","type_name":"对战","type_id":"7","game_name":"欢乐斗地主","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-20/5a629e1cb24a6.jpg","create_time":"1516333693"},{"counts":"2","type_name":"动作","type_id":"5","game_name":"猎魔传说","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-15/5a0ba30932c28.png","create_time":"1516333671"},{"counts":"1","type_name":"测试类型测试类型测试类型测试类型测试类型","type_id":"20","game_name":"测试游戏测试游戏测试游戏测试游戏测试游戏测试游戏测试游戏","icon":"","create_time":"1517363504"},{"counts":"1","type_name":"吃鸡","type_id":"18","game_name":"荒野行动","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-30/5a7013dc46d99.png","create_time":"1516882855"},{"counts":"1","type_name":"射击","type_id":"15","game_name":"枪魂","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-14/5a0aa452d1b2c.png","create_time":"1516413186"},{"counts":"1","type_name":"休闲","type_id":"14","game_name":"开心消消乐","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-20/5a629faf19aeb.jpg","create_time":"1516412862"},{"counts":"1","type_name":"棋牌","type_id":"10","game_name":"皇室战争","icon":"http://testh55.vlcms.com/Uploads/Picture/2018-01-20/5a629e46ec0ca.png","create_time":"1516333716"},{"counts":"1","type_name":"自由","type_id":"6","game_name":"挂挂三国","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-14/5a0abf08de203.jpg","create_time":"1516333678"}]
     */

    private int all;
    private List<GroupBean> group;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class GroupBean {
        /**
         * counts : 10
         * type_name : 角色扮演
         * type_id : 2
         * game_name : 边境之旅
         * icon : http://testh55.vlcms.com/Uploads/Picture/2018-01-19/5a619148b7119.png
         * create_time : 1516332795
         */

        public boolean isColor = false;
        private String counts;
        private String type_name;
        private String type_id;
        private String game_name;
        private String icon;
        private String create_time;

        public String getCounts() {
            return counts;
        }

        public void setCounts(String counts) {
            this.counts = counts;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
