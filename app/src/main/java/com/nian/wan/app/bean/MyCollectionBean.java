package com.nian.wan.app.bean;

import java.util.List;

/**
 * @Description: 我的收藏列表实体类
 * @Author: XiaYuShi
 * @Date: Created in 2017/11/7 9:38
 * @Modified By:
 * @Modified Date:
 */
public class MyCollectionBean {


    private List<CollectBean> collect;
    private List<FootBean> foot;

    public List<CollectBean> getCollect() {
        return collect;
    }

    public void setCollect(List<CollectBean> collect) {
        this.collect = collect;
    }

    public List<FootBean> getFoot() {
        return foot;
    }

    public void setFoot(List<FootBean> foot) {
        this.foot = foot;
    }

    public static class CollectBean {
        /**
         * bid : 18
         * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-23/5a16750d1e9dd.png
         * game_name : 我的世界
         * id : 167
         * game_type_id : 4
         * features : 风靡全球的沙盒游戏，全球超过1亿玩家的选择，挑战你的想象力与创造力
         * screenshot : 225,226,227,228,229
         * introduction : 《我的世界》中的游戏世界可称为一个迷你地球，它包含十几种特色各异的生物群系。玩家可以在不同的生态环境发展，与生物进行互动，挑战不同的怪物。~可能无限的游戏机制~合成、烧炼、附魔与酿造等系统可帮助玩家在生存模式中存活的更久。红石类组件、命令方块等极具深度的游戏系统，可以让玩家更多探索的可能。你可以使用红石类组件在生存模式中制作陷阱防卫怪物的进攻，也可以制作一些方便生存的设施，或是在创造模式中制作复杂的电路系统。你也可以使用命令方块实现一些完全不同的游戏方式。~与朋友们一起畅玩~通过好友系统玩家可以添加好友，与朋友一起联机探索世界。房主可以对房间设置权限与命令，也可为多人世界加入一些特色内容。与好
         * collect_status : 1
         * game_type_name : 模拟
         * cover :
         * play_num : 250
         * play_detail_url : /app.php?s=/GamePage/detail/game_id/167.html
         * play_url : http://testh55.vlcms.com/app.php?s=/Mobile/Game/open_game/game_id/167.html
         */

        private String bid;
        private String icon;
        private String game_name;
        private String id;
        private String game_type_id;
        private String features;
        private String screenshot;
        private String introduction;
        private String collect_status;
        private String game_type_name;
        private String cover;
        private int play_num;
        private String play_detail_url;
        private String play_url;
        private int sdk_version;
        private int xia_status;

        public int getSdk_version() {
            return sdk_version;
        }

        public void setSdk_version(int sdk_version) {
            this.sdk_version = sdk_version;
        }

        public int getXia_status() {
            return xia_status;
        }

        public void setXia_status(int xia_status) {
            this.xia_status = xia_status;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(String game_type_id) {
            this.game_type_id = game_type_id;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getScreenshot() {
            return screenshot;
        }

        public void setScreenshot(String screenshot) {
            this.screenshot = screenshot;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }

        public String getGame_type_name() {
            return game_type_name;
        }

        public void setGame_type_name(String game_type_name) {
            this.game_type_name = game_type_name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getPlay_num() {
            return play_num;
        }

        public void setPlay_num(int play_num) {
            this.play_num = play_num;
        }

        public String getPlay_detail_url() {
            return play_detail_url;
        }

        public void setPlay_detail_url(String play_detail_url) {
            this.play_detail_url = play_detail_url;
        }

        public String getPlay_url() {
            return play_url;
        }

        public void setPlay_url(String play_url) {
            this.play_url = play_url;
        }

        public int getSdk_Version() {
            return sdk_version;
        }

        public void setSdk_Version(int sdk_version) {
            this.sdk_version = sdk_version;
        }
    }

    public static class FootBean {
        /**
         * data : 11月12日
         * game_data : [{"bid":"64","icon":"http://testh55.vlcms.com/Uploads/Picture/2017-11-27/5a1b8117c670c.jpg","game_name":"蜀山奇侠传","id":"42","game_type_id":"3","features":"","screenshot":"241,242,243,244,245","introduction":"《蜀山奇侠传》是一款可以让玩家在美丽缥缈的仙侠世界内撰写自己传奇的挂机类RPG游戏，游戏风趣连贯的剧情故事、精美妖艳的人物形象、超级炫酷的技能特效还有用心打造的武器装备都为玩家提供了更加真实的代入感。游戏完美再现了恢弘壮丽的蜀山世界，带着玩家体验一次真实的修仙之路。是时候重回蜀山之巅，傲视群雄了。","collect_status":"2","date":"11月12日","game_type_name":"策略","cover":"","play_num":2654,"play_detail_url":"/app.php?s=/GamePage/detail/game_id/42.html","play_url":"http://testh55.vlcms.com/mobile.php?s=Game/open_game/game_id/42"}]
         */

        private String data;
        private List<GameDataBean> game_data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public List<GameDataBean> getGame_data() {
            return game_data;
        }

        public void setGame_data(List<GameDataBean> game_data) {
            this.game_data = game_data;
        }

        public static class GameDataBean {
            /**
             * bid : 64
             * icon : http://testh55.vlcms.com/Uploads/Picture/2017-11-27/5a1b8117c670c.jpg
             * game_name : 蜀山奇侠传
             * id : 42
             * game_type_id : 3
             * features :
             * screenshot : 241,242,243,244,245
             * introduction : 《蜀山奇侠传》是一款可以让玩家在美丽缥缈的仙侠世界内撰写自己传奇的挂机类RPG游戏，游戏风趣连贯的剧情故事、精美妖艳的人物形象、超级炫酷的技能特效还有用心打造的武器装备都为玩家提供了更加真实的代入感。游戏完美再现了恢弘壮丽的蜀山世界，带着玩家体验一次真实的修仙之路。是时候重回蜀山之巅，傲视群雄了。
             * collect_status : 2
             * date : 11月12日
             * game_type_name : 策略
             * cover :
             * play_num : 2654
             * play_detail_url : /app.php?s=/GamePage/detail/game_id/42.html
             * play_url : http://testh55.vlcms.com/mobile.php?s=Game/open_game/game_id/42
             */

            private String bid;
            private String icon;
            private String game_name;
            private String id;
            private String game_type_id;
            private String features;
            private String screenshot;
            private String introduction;
            private String collect_status;
            private String date;
            private String game_type_name;
            private String cover;
            private int play_num;
            private String play_detail_url;
            private String play_url;
            private int sdk_version;

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getGame_name() {
                return game_name;
            }

            public void setGame_name(String game_name) {
                this.game_name = game_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGame_type_id() {
                return game_type_id;
            }

            public void setGame_type_id(String game_type_id) {
                this.game_type_id = game_type_id;
            }

            public String getFeatures() {
                return features;
            }

            public void setFeatures(String features) {
                this.features = features;
            }

            public String getScreenshot() {
                return screenshot;
            }

            public void setScreenshot(String screenshot) {
                this.screenshot = screenshot;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getCollect_status() {
                return collect_status;
            }

            public void setCollect_status(String collect_status) {
                this.collect_status = collect_status;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getGame_type_name() {
                return game_type_name;
            }

            public void setGame_type_name(String game_type_name) {
                this.game_type_name = game_type_name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getPlay_num() {
                return play_num;
            }

            public void setPlay_num(int play_num) {
                this.play_num = play_num;
            }

            public String getPlay_detail_url() {
                return play_detail_url;
            }

            public void setPlay_detail_url(String play_detail_url) {
                this.play_detail_url = play_detail_url;
            }

            public String getPlay_url() {
                return play_url;
            }

            public void setPlay_url(String play_url) {
                this.play_url = play_url;
            }

            public int getSdk_Version() {
                return sdk_version;
            }

            public void setSdk_Version(int sdk_version) {
                this.sdk_version = sdk_version;
            }
        }
    }
}
