package com.baseeasy.baseframework.demoactivity;

import androidx.annotation.NonNull;

import com.baseeasy.commonlibrary.dictionary.DictionaryEnum;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/10
 * 邮箱：sos181@163.com
 * 描述：字典集合
 */
public class MyDictionary {

    /**
    * 工作类型
    */
    public  enum  WORK implements DictionaryEnum {
             GDGZ("固定工作","1"),
             LSGZ("临时工作","2"),
            WGZ("无工作","3"),
            LTXRY("离退休人员","4"),
            ZZRY("在职人员","5"),
            DJZYRY("登记失业人员","6"),
            WDJSYRY("未登记失业人员","7"),
            LHJYRY("灵活就业人员","8"),
            WG("务工","9"),
            WN("务农","10"),
            QT("其他","99");
              String name="";
              String code="";

        WORK(String name,String code){
            this.name=name;
            this.code=code;
          }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

       @NonNull
       @Override
       public String toString() {
           return name;
       }


    }

    /**
     * 民族nation
     */
    public enum NATION implements DictionaryEnum{

        HANZ("汉族","1"),
        MGZ("蒙古族","2"),
        HUIZ("回族","3"),
        ZANGZ("藏族","4"),
        WWEZ("维吾尔族","5"),
        MIAOZ("苗族","6"),
        YIZ("彝族","7"),
        ZHUANGZ("壮族","8"),
        BUYIZ("布依族","9"),
        CHAOXIANZ("朝鲜族","10"),
        MANZ("满族","11"),
        DONGZ("侗族","12"),
        YAOZ("瑶族","13"),
        BAIZ("白族","14"),
        TUJIAZ("土家族","15"),
        HANIZ("哈尼族","16"),
        HASAKEZ("哈萨克族","17"),
        DAIZ("傣族","18"),
        LIZ("黎族","19"),
        LISUZ("傈僳族","20"),
        WAZ("佤族","21"),
        SHEZ("畲族","22"),
        GAOSHANZ("高山族","23"),
        LAHUZ("拉祜族","24"),
        SHUIZ("水族","25"),
        DONGXIANGZ("东乡族","26"),
        NAXIZ("纳西族","27"),
        JINGPOZ("景颇族","28"),
        KEKZZ("柯尔克孜族","29"),
        TUZ("土族","30"),
        DWRZ("达斡尔族","31"),
        MLZ("仫佬族","32"),
        QIANGZ("羌族","33"),
        BULANGZ("布朗族","34"),
        SAHLAZ("撒拉族","35"),
        MAONANZ("毛难族","36"),
        GELAOZ("仡佬族","37"),
        XIBOZ("锡伯族","38"),
        ACHANGZ("阿昌族","39"),
        PUMIZ("普米族","40"),
        TAJIKEZ("塔吉克族","41"),
        NUZ("怒族","42"),
        WZBKZ("乌孜别克族","43"),
        ELSZ("俄罗斯族","44"),
        EWKZ("鄂温克族","45"),
        BENGLONGZ("崩龙族","46"),
        BAOANZ("保安族","47"),
        YUGUZ("裕固族","48"),
        JINGZ("京族","49"),
        TATAERZ("塔塔尔族","50"),
        DULONGZ("独龙族","51"),
        ELCZ("鄂伦春族","52"),
        HEZEZ("赫哲族","53"),
        MENBAZ("门巴族","54"),
        LUOBAZ("珞巴族","55"),
        JINUOZ("基诺族","56");
        String name="";
        String code="";

        NATION(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 救助对象类别 category
     */
    public enum CATEGORY implements DictionaryEnum{
        NKQYRY("农垦企业人员","1"),
        SGQYRY("森工企业人员","2"),
        LLSFRY("两劳释放人员","3"),
        SJGQQJ("散居归侨侨眷","4"),
        FNSKYM("非农水库移民","5"),
        GXBYS("高校毕业生","6"),
        TYJR("退役军人","7"),
        ZJJZRY("宗教教职人员","8"),
        YFDX("优抚对象","9"),
        TDJZDX("特定救助对象","10"),
        CTMZDX("传统民政对象","11"),
        JJTZRY("60年代精简退职人员","12"),
        WCBJTQYRY("未参保集体企业人员","13"),
        GMDTCQYRY("国民党投诚起义人员","14"),
        LDMF("劳动模范","15"),
        DSZN("独生子女","16"),
        FTDJZRY("非特定救助人员","17"),
        KDSFRY("宽大释放人员","18"),
        SKYMNH("水库移民（农业户口）","19"),
        GJHQJ("归眷和侨眷","20"),
        ZMYPRY("摘帽右派人员","22"),
        YSMTHGY("有色煤炭核工业","23"),
        FCBTZQ("返城病退知青","24"),
        RKJSMZ("人口较少民族","25"),
        XYQYM("休渔期渔民","26"),
        GSYZYS("工商业者遗属","27"),
        LAOREN70("70岁以上老人","28"),
        BIANMIN("边民","29"),
        STUDENT("高中阶级在读学生","30"),
        JSDX("计生对象","31"),
        SDRY("失地人员","32"),
        TWOGIRL("两女户","33"),
        OTHER("其他对象","99");
        String name="";
        String code="";

        CATEGORY(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 住房类型 house_type
     */
    public enum HOUSETYPE implements DictionaryEnum{
        ZLGY("租赁公有","1"),
        ZLSY("租赁私有","2"),
        ZY("自有","3"),
        JZ("借住","4"),
        SPF("商品房","5"),
        JJSYF("经济适用房","6"),
        DWFLF("单位福利房","7"),
        CQAZF("拆迁安置房","8"),
        ZJZF("自建住房","9"),
        ZYQT("自有其他","10"),
        ZL("租赁","11"),
        OTHER("其他","99");
        String name="";
        String code="";

        HOUSETYPE(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 性别
     */
    public enum SEX implements DictionaryEnum{
        MAN("男","1"),
        WOMAN("女","2");
        String name="";
        String code="";

        SEX(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 关系
     */
    public enum RELATIONS implements DictionaryEnum{
        SELF("本人","1"),
        MATE("配偶","2"),
        YZN("养子女","3"),
        SON("儿子","4"),
        DATGHTER("女儿","5"),
        PARENTS("父母","6"),
        GRANDPARENTS("祖父母","7"),
        BROTHERSANDSISTERS("兄弟姐妹","8"),
        GRANDCHILDREN("孙子女","9"),
        GRANDPARENTS2("（外）祖父母","10"),
        ERXI("儿媳","11"),
        NVXU("女婿","12"),
        ZHIER("侄儿（女）","13"),
        WAISHENG("外甥子（女）","14"),
        OTHER("其他","15");
        String name="";
        String code="";

        RELATIONS(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 婚姻状态
     */
    public enum MARRYS implements DictionaryEnum{
        WEIHUN("未婚","1"),
        YIHUN("已婚","2"),
        LIYI("离异","3"),
        SANGOU("丧偶","4"),
        OTHER("未说明的婚姻状态","99");
        String name="";
        String code="";

        MARRYS(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 是否
     */
    public enum WHETHER implements DictionaryEnum{
        YES("是","1"),
        NO("否","2");
        String name="";
        String code="";

        WHETHER(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 表单类型
     */
    public enum FORMTYPE implements DictionaryEnum{
        RHHCB("入户核查表","0"),
        ZRGZS("责任告知书","1");
        String name="";
        String code="";

        FORMTYPE(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 数据、信息类型 0 特困 1临时救助 2入户核查表
     */
    public enum INFOTYPE implements DictionaryEnum{
        TKJZ("特困救助","0"),
        LSJZ("临时救助","1"),
        RHHCB("入户核查表","2");
        String name="";
        String code="";

        INFOTYPE(String name,String code){
            this.name=name;
            this.code=code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }


    public enum IS_APPLY implements  DictionaryEnum{
        XXSHOU("现享受","1"),
        XSQING(" 新申请","2")

    ;

    String name="";
    String code="";
    IS_APPLY(String name,String code){
        this.name=name;
        this.code=code;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    public enum DbType implements  DictionaryEnum{
        A("A","1"),
        B("B","2"),
        C("C","3") ;

        String name="";
        String code="";
        DbType(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    public enum HJXZ implements  DictionaryEnum{
        FNY("非农业","1"),
       NY("农业","2");


        String name="";
        String code="";
        HJXZ(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 邻里走访中的工作
     */
    public enum LLZFWORK implements DictionaryEnum{
        ANSWER_A("A 企事业单位管理人员","A"),
        ANSWER_B("B 企业工人","B"),
        ANSWER_C("C 企事业单位一般职员","C"),
        ANSWER_D("D 商业服务从业人员","D"),
        ANSWER_E("E 个体户","E"),
        ANSWER_F("F 务工人员","F"),
        ANSWER_G("G 务农人员","G"),
        ANSWER_H("H 其他","H"),
        ANSWER_I("I 未知","I");
        String name="";
        String code="";
        LLZFWORK(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 邻里走访中的年龄
     * {"a 0-18周岁", "b 19-50周岁", "c  51-70周岁", "d 71周岁以上", "e 未知"};
     */
    public enum LLZFAGE implements DictionaryEnum{
        ANSWER_A("a 0-18周岁","a"),
        ANSWER_B("b 19-50周岁","b"),
        ANSWER_C("c  51-70周岁","c"),
        ANSWER_D("d 71周岁以上","d"),
        ANSWER_E("e 未知","e");
        String name="";
        String code="";
        LLZFAGE(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

    /**
     * 入户核查中的致贫原因
     */
    public enum ZPYY implements DictionaryEnum{
        JB("疾病","1"),
        ZH("灾害","2"),
        CJ("残疾","3"),
        QFLDL("缺乏劳动力","4"),
        SY("失业","5"),
        SD("失地","6"),
        OTHER("其他","7"),
        YX("因学","8");//内蒙低保正式库中没有该项
        String name="";
        String code="";
        ZPYY(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }
    /**
     * 入户核查中的装修情况
     */
    public enum ZXQK implements DictionaryEnum{
        JB("简装","0"),
        ZH("毛坯","1"),
        CJ("精装","2");
        String name="";
        String code="";
        ZXQK(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }

    }
    /**
     * 入户核查中的住房类型
     */
    public enum ZFLX implements DictionaryEnum{
        ZY("自有","0"),
        GY("公有","1"),
        JZ("借住","2"),
        ZL("租赁","3");
        String name="";
        String code="";
        ZFLX(String name,String code){
            this.name=name;
            this.code=code;
        }
        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }
        @NonNull
        @Override
        public String toString() {
            return name;
        }


    }

}
