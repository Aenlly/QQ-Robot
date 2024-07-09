package top.aenlly.qqrobot.tps.pixiv;

import lombok.Data;

import java.util.List;

@Data
public class MessageVO {
    private List<MessageData> data;

    private String message;


    /**
     * 数据
     *
     * @author Aenlly||tnw
     * @create 2024/07/09 21:48:03
     * @since 1.0.0
     */
    @Data
    public static class MessageData {

        /**
         * id
         */
        private Integer id;
        /**
         * 艺术家 ID
         */
        private Integer artistId;
        /**
         * 标题
         */
        private String title;
        /**
         * 类型
         */
        private String type;
        /**
         * 标题
         */
        private String caption;
        /**
         * 创建日期
         */
        private String createDate;
        /**
         * 图片网址
         */
        private List<ImageUrl> imageUrls;
        /**
         * 页数
         */
        private Integer pageCount;
        /**
         * 限制
         */
        private Integer restrict;
        /**
         * 理智水平
         */
        private Integer sanityLevel;
        /**
         * 标签
         */
        private List<Tag> tags;
        /**
         * 书签总数
         */
        private Integer totalBookmarks;
        /**
         * 总视图
         */
        private Integer totalView;
        /**
         * 高度
         */
        private Integer height;
        /**
         * 宽度
         */
        private Integer width;
        /**
         * XRESTRICT
         */
        private String xrestrict;

    }

    /**
     * 图片网址
     *
     * @author Aenlly||tnw
     * @create 2024/07/09 21:48:46
     * @since 1.0.0
     */
    @Data
    public static class ImageUrl {
        /**
         * 大
         */
        private String large;
        /**
         * 中等
         */
        private String medium;
        /**
         * 源语言
         */
        private String original;
        /**
         * 方形中等
         */
        private String squareMedium;
    }

    /**
     * 标记
     *
     * @author Aenlly||tnw
     * @create 2024/07/09 21:49:03
     * @since 1.0.0
     */
    @Data
    public static class Tag {
        /**
         * id
         */
        private Integer id;

        /**
         * 名字
         */
        private String name;
        /**
         * 翻译过名字
         */
        private String translatedName;
    }

}
