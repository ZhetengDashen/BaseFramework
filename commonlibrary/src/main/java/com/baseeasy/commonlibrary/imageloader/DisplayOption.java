package com.baseeasy.commonlibrary.imageloader;

import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
/**
 * 作者：WangZhiQiang
 * 时间：2019/4/28
 * 邮箱：sos181@163.com
 * 描述：
 */
public  class DisplayOption {
        public static final int NONE = -1;
        /**
         * 加载失败的资源id
         */
        private int loadErrorResId = R.mipmap.image_error;
        /**
         * 占位资源id
         */
        private int placeHolderResId = R.mipmap.image_bg;

        /**
         * 宽
         */
        private  int width=NONE;
        /**
         * 高
         */
        private  int height=NONE;



        public DisplayOption(int loadErrorResId, int placeHolderResId, int width, int height) {
                this.loadErrorResId = loadErrorResId;
                this.placeHolderResId = placeHolderResId;
                this.width = width;
                this.height = height;
        }

        public int getLoadErrorResId() {
                return loadErrorResId;
        }

        public void setLoadErrorResId(int loadErrorResId) {
                this.loadErrorResId = loadErrorResId;
        }

        public int getPlaceHolderResId() {
                return placeHolderResId;
        }

        public void setPlaceHolderResId(int placeHolderResId) {
                this.placeHolderResId = placeHolderResId;
        }

        public int getWidth() {
                return width;
        }

        public void setWidth(int width) {
                this.width = width;
        }

        public int getHeight() {
                return height;
        }

        public void setHeight(int height) {
                this.height = height;
        }

        public static class  Builder{

                private int loadErrorResId = NONE;

                private int placeHolderResId = NONE;

                private  int width=NONE;

                private  int height=NONE;


                public Builder setLoadErrorResId(int loadErrorResId) {
                        this.loadErrorResId = loadErrorResId;
                        return this;
                }


                public Builder setPlaceHolderResId(int placeHolderResId) {
                        this.placeHolderResId = placeHolderResId;
                        return this;
                }



                public Builder setWidth(int width) {
                        this.width = width;
                        return this;
                }



                public Builder setHeight(int height) {
                        this.height = height;
                        return this;
                }
                public DisplayOption create() {
                        return new DisplayOption(loadErrorResId, placeHolderResId, width,height);
                }
        }
    }