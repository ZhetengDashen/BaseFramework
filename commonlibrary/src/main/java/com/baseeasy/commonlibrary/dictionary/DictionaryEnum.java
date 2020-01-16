package com.baseeasy.commonlibrary.dictionary;
/**
 * 作者：WangZhiQiang
 * 时间：2019/10/25
 * 邮箱：sos181@163.com
 * 描述：字典枚举
 *
 *
 *   public  enum  WORK implements DictionaryEnum {
 *              GDGZ("固定工作","1"),
 *              LSGZ("临时工作","2"),
 *             WGZ("无工作","3"),
 *             LTXRY("离退休人员","4"),
 *             ZZRY("在职人员","5"),
 *             DJZYRY("登记失业人员","6"),
 *             WDJSYRY("未登记失业人员","7"),
 *             LHJYRY("灵活就业人员","8"),
 *             WG("务工","9"),
 *             WN("务农","10"),
 *             QT("其他","99");
 *               String name="";
 *               String code="";
 *
 *         WORK(String name,String code){
 *             this.name=name;
 *             this.code=code;
 *           }
 *
 *         public String getName() {
 *             return name;
 *         }
 *
 *         public void setName(String name) {
 *             this.name = name;
 *         }
 *
 *         public String getCode() {
 *             return code;
 *         }
 *
 *         public void setCode(String code) {
 *             this.code = code;
 *         }
 *
 *        @NonNull
 *        @Override
 *        public String toString() {
 *            return name;
 *        }
 *
 *
 *     }
 *
 */
public interface DictionaryEnum {
    String getCode();
    String getName();
}