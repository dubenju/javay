/**
 *
 */
package javay.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javay.distance.city.ModelCity;

/**
 * @author DBJ
 *
 */
public class UCity {
    private static Map<String, ModelCity> modelCities = new HashMap<String, ModelCity>();
    static {
        modelCities.put("北京" , new ModelCity("北京", 116.41667, 39.91667, 31.2));
        modelCities.put("上海" , new ModelCity("上海", 121.43333, 31.13, 4.5));
        modelCities.put("天津" , new ModelCity("天津", 117.2, 39.13333, 3.3));
        modelCities.put("重庆" , new ModelCity("重庆", 106.547565, 29.548940, 259.1));
        modelCities.put("哈尔滨" , new ModelCity("哈尔滨", 126.63333, 45.75, 171.7)); // ★
        modelCities.put("长春" , new ModelCity("长春", 125.35, 43.88333, 236.8));
        modelCities.put("沈阳" , new ModelCity("沈阳", 123.38333, 41.8, 41.6));
        modelCities.put("呼和浩特" , new ModelCity("呼和浩特", 111.65, 40.82, 1063));
        modelCities.put("石家庄" , new ModelCity("石家庄", 114.48333, 38.03333, 80.5));
        modelCities.put("乌鲁木齐" , new ModelCity("乌鲁木齐", 87.68333, 43.76667, 917.9));
        modelCities.put("兰州" , new ModelCity("兰州", 103.73333, 36.03333, 1520));
        modelCities.put("西宁" , new ModelCity("西宁", 101.75, 36.56667, 2261.2));
        modelCities.put("西安" , new ModelCity("西安", 108.95, 34.26667, 396.9));
        modelCities.put("银川" , new ModelCity("银川", 106.26667, 38.46667, 1111.5));
        modelCities.put("郑州" , new ModelCity("郑州", 113.65, 34.76667, 110.4));
        modelCities.put("济南" , new ModelCity("济南", 117.02, 36.65, 51.6));
        modelCities.put("太原" , new ModelCity("太原", 112.53333, 37.86667, 777.9));
        modelCities.put("合肥" , new ModelCity("合肥", 117.27, 31.86, 29.8));
        modelCities.put("武汉" , new ModelCity("武汉", 114.31667, 30.51667, 23.3));
        modelCities.put("长沙" , new ModelCity("长沙", 113, 28.21667, 44.9));
        modelCities.put("南京" , new ModelCity("南京", 118.78333, 32.05, 8.9));
        modelCities.put("成都" , new ModelCity("成都", 104.06667, 30.66667, 505.9));
        modelCities.put("贵阳" , new ModelCity("贵阳", 106.71667, 26.56667, 1071.2));
        modelCities.put("昆明" , new ModelCity("昆明", 102.73333, 25.05, 1891.4));
        modelCities.put("南宁" , new ModelCity("南宁", 108.33, 22.84, 72.2));
        modelCities.put("拉萨" , new ModelCity("拉萨", 91, 29.6, 3658)); // ★
        modelCities.put("杭州" , new ModelCity("杭州", 120.155058, 30.273993, 41.7));
        modelCities.put("南昌" , new ModelCity("南昌", 115.9, 28.68333, 46.7));
        modelCities.put("广州" , new ModelCity("广州", 113.23333, 23.16667, 6.6));
        modelCities.put("福州" , new ModelCity("福州", 119.3, 26.08333, 84));
        modelCities.put("台北" , new ModelCity("台北", 121.5, 25.05, 9));
        modelCities.put("海口" , new ModelCity("海口", 110.35, 20.01667, 14.1)); // ★
        modelCities.put("香港" , new ModelCity("香港", 114.1, 22.2, 32));
        modelCities.put("澳门" , new ModelCity("澳门", 113.5, 22.2, 48.1));
        modelCities.put("青岛" , new ModelCity("青岛", 120.33333, 36.06667, 76));
        modelCities.put("大连" , new ModelCity("大连", 121.62, 38.92, 40));
        modelCities.put("深圳" , new ModelCity("深圳", 114.07, 22.62, 76));
        modelCities.put("丹东" , new ModelCity("丹东", 124.37, 40.13, 76));
        modelCities.put("抚顺" , new ModelCity("抚顺", 123.97, 41.97, 76));
        modelCities.put("吉林" , new ModelCity("吉林", 126.57, 43.87, 76));
        modelCities.put("廊坊" , new ModelCity("廊坊", 116.65, 39.53, 0));
        modelCities.put("沧州" , new ModelCity("沧州", 116.6333333, 38.33, 0));
        modelCities.put("德州" , new ModelCity("德州", 116.29, 37.45, 0));
        modelCities.put("泰安" , new ModelCity("泰安", 117.13, 36.18, 0));
        modelCities.put("曲阜" , new ModelCity("曲阜", 116.98, 35.59, 0));
        modelCities.put("滕州" , new ModelCity("滕州", 117.0916667, 35.05833333, 0));
        modelCities.put("枣庄" , new ModelCity("枣庄", 117.57, 34.86, 0));
        modelCities.put("徐州" , new ModelCity("徐州", 117.2, 34.26, 0));
        modelCities.put("宿州" , new ModelCity("宿州", 116.97, 33.63, 0));
        modelCities.put("蚌埠" , new ModelCity("蚌埠", 117.34, 32.93, 0));
        modelCities.put("定远" , new ModelCity("定远", 117.68, 32.52, 0));
        modelCities.put("滁州" , new ModelCity("滁州", 118.31, 32.33, 0));
        modelCities.put("镇江" , new ModelCity("镇江", 119.44, 32.2, 0));
        modelCities.put("丹阳" , new ModelCity("丹阳", 119.55, 32, 0));
        modelCities.put("常州" , new ModelCity("常州", 119.95, 31.79, 0));
        modelCities.put("无锡" , new ModelCity("无锡", 120.29, 31.59, 0));
        modelCities.put("苏州" , new ModelCity("苏州", 120.62, 31.32, 0));
        modelCities.put("昆山" , new ModelCity("昆山", 120.95, 31.39, 0));
        modelCities.put("咸宁" , new ModelCity("咸宁", 114.28, 29.87, 0));
        modelCities.put("赤壁" , new ModelCity("赤壁", 113.88 , 29.72, 0));
        modelCities.put("岳阳" , new ModelCity("岳阳", 113.09, 29.37, 0));
        modelCities.put("汨罗" , new ModelCity("汨罗", 113.08, 28.8, 0));
        modelCities.put("株洲" , new ModelCity("株洲", 113.16, 27.83, 0));
        modelCities.put("衡山" , new ModelCity("衡山", 112.86, 27.25, 0));
        modelCities.put("衡阳" , new ModelCity("衡阳", 112.61, 26.89, 0));
        modelCities.put("耒阳" , new ModelCity("耒阳", 112.85, 26.42, 0));
        modelCities.put("郴州" , new ModelCity("郴州", 113, 25.79, 0));
        modelCities.put("韶关" , new ModelCity("韶关", 113.597507, 24.810346, 0));
        modelCities.put("清远" , new ModelCity("清远", 113.056042, 23.681525, 0));
        modelCities.put("双城" , new ModelCity("双城", 126.32, 45.53, 0));
        modelCities.put("扶余" , new ModelCity("扶余", 126.02 ,44.98, 0));
        modelCities.put("德惠" , new ModelCity("德惠", 125.68, 44.52, 0));
        modelCities.put("公主岭" , new ModelCity("公主岭", 124.82, 43.5, 0));
        modelCities.put("四平" , new ModelCity("四平", 124.37, 43.17, 0));
        modelCities.put("昌图" , new ModelCity("昌图", 124.13, 42.8, 0));
        modelCities.put("开原" , new ModelCity("开原", 124.03, 42.53, 0));
        modelCities.put("铁岭" , new ModelCity("铁岭", 123.85, 42.32, 0));
        modelCities.put("辽阳" , new ModelCity("辽阳", 123.17, 41.28, 0));
        modelCities.put("鞍山" , new ModelCity("鞍山", 122.85, 41.12, 0));
        modelCities.put("海城" , new ModelCity("海城", 122.75, 40.85, 0));
        modelCities.put("营口" , new ModelCity("营口", 122.18, 40.65, 0));
        modelCities.put("盖州" , new ModelCity("盖州", 122.35, 40.4, 0));
        modelCities.put("鲅鱼圈" , new ModelCity("鲅鱼圈", 122.12, 40.27, 0));
        modelCities.put("瓦房店" , new ModelCity("瓦房店", 122, 39.62, 0));
        modelCities.put("普兰店" , new ModelCity("普兰店", 121.95, 39.40, 0));

        modelCities.put("虎门" , new ModelCity("虎门", 113.673138, 22.860371, 0));
        modelCities.put("龙华" , new ModelCity("龙华", 114.022431, 22.651194, 0));
        modelCities.put("落马洲" , new ModelCity("落马洲", 114.064765, 22.513289, 0));
        modelCities.put("锦上路" , new ModelCity("锦上路", 114.068817, 22.421330, 0));
        modelCities.put("西九龙" , new ModelCity("西九龙", 114.1598013, 22.3036511, 0));
        modelCities.put("保定" , new ModelCity("保定", 115.473333333333, 38.87, 0));
        modelCities.put("高邑" , new ModelCity("高邑", 114.58, 37.62, 0));
        modelCities.put("邢台" , new ModelCity("邢台", 114.48, 37.05, 0));
        modelCities.put("邯郸" , new ModelCity("邯郸", 114.47, 36.6, 0));
        modelCities.put("安阳" , new ModelCity("安阳", 114.35, 36.1, 0));
        modelCities.put("鹤壁" , new ModelCity("鹤壁", 114.17, 35.9, 0));
        modelCities.put("新乡" , new ModelCity("新乡", 113.85, 35.31, 0));
        modelCities.put("许昌" , new ModelCity("许昌", 113.81, 34.02, 0));
        modelCities.put("漯河" , new ModelCity("漯河", 114.02, 33.56, 0));
        modelCities.put("驻马店" , new ModelCity("驻马店", 114.02, 32.98, 0));
        modelCities.put("明港" , new ModelCity("明港", 114.12, 32.1, 0));
        modelCities.put("信阳" , new ModelCity("信阳", 114.08, 32.13, 0));
        modelCities.put("孝感" , new ModelCity("孝感", 113.91, 31.92, 0));
        modelCities.put("荥阳" , new ModelCity("荥阳", 113.35, 34.79, 0));
        modelCities.put("巩义" , new ModelCity("巩义", 112.98, 34.77, 0));
        modelCities.put("洛阳" , new ModelCity("洛阳", 112.44, 34.7, 0));
        modelCities.put("渑池" , new ModelCity("渑池", 111.75, 34.76, 0));
        modelCities.put("三门峡" , new ModelCity("三门峡", 111.19, 34.76, 0));
        modelCities.put("灵宝" , new ModelCity("灵宝", 110.85, 34.52, 0));
//        modelCities.put("华山" , new ModelCity("华山", 110.09, 34.58, 0));
        modelCities.put("渭南" , new ModelCity("渭南", 109.5, 34.52, 0));
        modelCities.put("临潼" , new ModelCity("临潼", 109.22, 34.38, 0));
        modelCities.put("咸阳" , new ModelCity("咸阳", 108.72, 34.36, 0));
        modelCities.put("杨凌" , new ModelCity("杨凌", 108.083055555556, 34.2716666666667, 0));
        modelCities.put("五丈原" , new ModelCity("五丈原", 107.608657, 34.275303, 0));
        modelCities.put("宝鸡" , new ModelCity("宝鸡", 107.15, 34.38, 0));
        modelCities.put("P点" , new ModelCity("P点", 124.699594, 40.330009, 0)); // ★
        modelCities.put("Q点" , new ModelCity("Q点", 124.695309, 40.322623, 0));
        modelCities.put("古楼子村" , new ModelCity("古楼子村", 124.672272, 40.318582, 0));
        modelCities.put("大蒲石河村" , new ModelCity("大蒲石河村", 124.693677, 40.334060, 0));
        modelCities.put("古楼子乡" , new ModelCity("古楼子乡", 124.6576507, 40.315684, 0));
        modelCities.put("丹东" , new ModelCity("丹东", 124.387597, 40.123787, 0));
        modelCities.put("嘉兴" , new ModelCity("嘉兴", 120.763605, 30.764118, 0));
        modelCities.put("诸暨" , new ModelCity("诸暨", 120.23, 29.71, 0));
        modelCities.put("义乌" , new ModelCity("义乌", 120.06, 29.32, 0));
        modelCities.put("金华" , new ModelCity("金华", 119.64, 29.12, 0));
        modelCities.put("衢州" , new ModelCity("衢州", 118.88, 28.97, 0));
        modelCities.put("江山" , new ModelCity("江山", 118.61, 28.74, 0));
        modelCities.put("玉山" , new ModelCity("玉山", 118.25, 28.68, 0));
        modelCities.put("上饶" , new ModelCity("上饶", 117.97, 28.47, 0));
        modelCities.put("弋阳" , new ModelCity("弋阳", 117.43, 28.42, 0));
        modelCities.put("鹰潭" , new ModelCity("鹰潭", 117.02, 28.23, 0));
        modelCities.put("抚州" , new ModelCity("抚州", 116.34, 28, 0));
        modelCities.put("进贤" , new ModelCity("进贤", 116.26, 28.37, 0));
        modelCities.put("高安" , new ModelCity("高安", 115.38, 28.42, 0));
        modelCities.put("新余" , new ModelCity("新余", 114.92, 27.81, 0));
        modelCities.put("宜春" , new ModelCity("宜春", 114.38, 27.81, 0));
        modelCities.put("萍乡" , new ModelCity("萍乡", 113.85, 27.6, 0));
        modelCities.put("醴陵" , new ModelCity("醴陵", 113.5, 27.67, 0));
        modelCities.put("湘潭" , new ModelCity("湘潭", 112.91, 27.87, 0));
        modelCities.put("韶山" , new ModelCity("韶山", 112.528899, 27.921098, 0));
        modelCities.put("娄底" , new ModelCity("娄底", 111.96, 27.71, 0));
        modelCities.put("坪上" , new ModelCity("坪上", 111.531898, 27.533595, 0));
        modelCities.put("洋溪" , new ModelCity("洋溪", 111.187644, 27.662551, 0));
        modelCities.put("隆回" , new ModelCity("隆回", 111.04, 27.13, 0));
        modelCities.put("北斗溪乡" , new ModelCity("北斗溪乡", 110.589158, 27.574028, 0));
        modelCities.put("怀化" , new ModelCity("怀化", 109.95, 27.52, 0));
        modelCities.put("芷江" , new ModelCity("芷江", 109.78, 27.44, 0));
        modelCities.put("晃西" , new ModelCity("晃西", 109.195611, 27.347833, 0));
        modelCities.put("玉屏" , new ModelCity("玉屏", 108.91, 27.24, 0));
        modelCities.put("三穗" , new ModelCity("三穗", 108.675283, 26.954286, 0));
        modelCities.put("台江" , new ModelCity("台江", 108.329193, 26.663953, 0));
        modelCities.put("凯里" , new ModelCity("凯里", 107.891671, 26.529183, 0));
        modelCities.put("贵定" , new ModelCity("贵定", 107.229787, 26.576133, 0));
        modelCities.put("平坝" , new ModelCity("平坝", 106.255081, 26.402071, 0));
        modelCities.put("安顺" , new ModelCity("安顺", 105.943108, 26.228811, 0));
        modelCities.put("黄果树" , new ModelCity("黄果树", 105.6799, 26.014022, 0));
        modelCities.put("普安" , new ModelCity("普安", 104.966730, 25.787850, 0));
        modelCities.put("盘县" , new ModelCity("盘县", 104.64, 25.81, 0));
        modelCities.put("富源" , new ModelCity("富源", 104.24, 25.67, 0));
        modelCities.put("曲靖" , new ModelCity("曲靖", 103.79, 25.51, 0));
        modelCities.put("小哨" , new ModelCity("小哨", 102.992686, 25.165894, 0));
        modelCities.put("亦庄" , new ModelCity("亦庄", 116.481228, 39.799943, 0));
        modelCities.put("永乐" , new ModelCity("永乐", 116.789349, 39.709295, 0));
        modelCities.put("武清" , new ModelCity("武清", 117.05, 39.4, 0));
        modelCities.put("天津" , new ModelCity("天津", 117.2, 39.13, 0));
        modelCities.put("塘沽" , new ModelCity("塘沽", 117.641659, 39.026798, 0));
        modelCities.put("福清" , new ModelCity("福清", 119.383745, 25.720124, 0));
        modelCities.put("莆田" , new ModelCity("莆田", 119.007771, 25.454067, 0));
        modelCities.put("泉州" , new ModelCity("泉州", 118.676766, 24.873019, 0));
        modelCities.put("晋江" , new ModelCity("晋江", 118.552376, 24.780982, 0));
        modelCities.put("厦门" , new ModelCity("厦门", 118.089421, 24.479784, 0));
        modelCities.put("江宁" , new ModelCity("江宁", 118.839971, 31.952779, 0));
        modelCities.put("句容" , new ModelCity("句容", 119.168665, 31.944804, 0));
        modelCities.put("溧水" , new ModelCity("溧水", 119.028508, 31.651233, 0));
        modelCities.put("瓦屋山" , new ModelCity("瓦屋山", 119.274158, 31.558292, 0));
        modelCities.put("溧阳" , new ModelCity("溧阳", 119.484189, 31.416740, 0));
        modelCities.put("宜兴" , new ModelCity("宜兴", 119.823542, 31.340385, 0));
        modelCities.put("长兴" , new ModelCity("长兴", 119.910944, 31.026567, 0));
        modelCities.put("湖州" , new ModelCity("湖州", 120.086873, 30.894236, 0));
        modelCities.put("德清" , new ModelCity("德清", 119.977372, 30.542292, 0));
        modelCities.put("仙西" , new ModelCity("仙西", 118.904687, 32.128294, 0));
        modelCities.put("宝华" , new ModelCity("宝华", 119.010795, 32.152659, 0));
        modelCities.put("镇江" , new ModelCity("镇江", 119.44, 32.2, 0));
        modelCities.put("丹徒" , new ModelCity("丹徒", 119.44, 32.2, 0));
        modelCities.put("丹阳" , new ModelCity("丹阳", 119.55, 32, 0));
        modelCities.put("常州" , new ModelCity("常州", 119.95, 31.79, 0));
        modelCities.put("戚墅堰" , new ModelCity("戚墅堰", 120.06751, 31.722607, 0));
        modelCities.put("惠山" , new ModelCity("惠山", 120.29847, 31.68104, 0));
        modelCities.put("无锡" , new ModelCity("无锡", 120.29, 31.59, 0));
        modelCities.put("苏州" , new ModelCity("苏州", 120.62, 31.32, 0));
        modelCities.put("阳澄湖" , new ModelCity("阳澄湖", 120.861857, 31.381064, 0));
        modelCities.put("昆山" , new ModelCity("昆山", 120.95, 31.39, 0));
        modelCities.put("陆家浜" , new ModelCity("陆家浜", 121.048377, 31.314488, 0));
        modelCities.put("安亭" , new ModelCity("安亭", 121.15859, 31.313234, 0));
        modelCities.put("南翔" , new ModelCity("南翔", 121.309137, 31.279937, 0));
        modelCities.put("松江" , new ModelCity("松江", 121.24, 31, 0));
        modelCities.put("金山" , new ModelCity("金山", 121.16, 30.89, 0));
        modelCities.put("嘉善" , new ModelCity("嘉善", 120.92, 30.84, 0));
        modelCities.put("嘉兴" , new ModelCity("嘉兴", 120.76, 30.77, 0));
        modelCities.put("桐乡" , new ModelCity("桐乡", 120.54, 30.64, 0));
        modelCities.put("海宁" , new ModelCity("海宁", 120.685244, 30.533925, 0));
        modelCities.put("余杭" , new ModelCity("余杭", 120.3, 30.43, 0));
        modelCities.put("深圳公明" , new ModelCity("深圳公明", 113.894664, 22.776304, 0));
        modelCities.put("东涌" , new ModelCity("东涌", 113.454975, 22.884636, 0));
        // modelCities.put("东涌" , new ModelCity("东涌", 113.454974999999, 22.884636, 0));
        modelCities.put("虎门" , new ModelCity("虎门", 113.673138, 22.860371, 0));
        modelCities.put("光明" , new ModelCity("光明", 113.943509, 22.76324, 0));
        modelCities.put("福田" , new ModelCity("福田", 114.055071, 22.521411, 0));
        modelCities.put("六安" , new ModelCity("六安", 116.49, 31.73, 0));
        modelCities.put("汉口" , new ModelCity("汉口", 114.254978, 30.618449, 0));
        modelCities.put("潜江" , new ModelCity("潜江", 112.900066, 30.401897, 0));
        modelCities.put("荆州" , new ModelCity("荆州", 112.239749, 30.334971, 0));
        modelCities.put("枝江" , new ModelCity("枝江", 111.760535, 30.425732, 0));
        modelCities.put("宜昌" , new ModelCity("宜昌", 111.286454, 30.69187, 0));
        modelCities.put("巴东" , new ModelCity("巴东", 110.340756, 31.042197, 0));
        modelCities.put("恩施" , new ModelCity("恩施", 109.488114, 30.271909, 0));
        modelCities.put("万州" , new ModelCity("万州", 108.408594, 30.80778, 0));
        modelCities.put("潼南" , new ModelCity("潼南", 106.22, 30.03, 0));
        modelCities.put("遂宁" , new ModelCity("遂宁", 105.58, 30.52, 0));
        modelCities.put("南充" , new ModelCity("南充", 106.110697, 30.837595, 0));
        modelCities.put("淮南" , new ModelCity("淮南", 116.98, 32.62, 0));
        modelCities.put("长临河" , new ModelCity("长临河", 117.461696, 31.690324, 0));
        modelCities.put("巢湖" , new ModelCity("巢湖", 117.87, 31.62, 0));
        modelCities.put("铜陵" , new ModelCity("铜陵", 117.82, 30.93, 0));
        modelCities.put("南陵" , new ModelCity("南陵", 118.32, 30.91, 0));
        modelCities.put("泾县" , new ModelCity("泾县", 118.41, 30.68, 0));
        modelCities.put("旌德" , new ModelCity("旌德", 118.540494, 30.286154, 0));
        modelCities.put("绩溪" , new ModelCity("绩溪", 118.57, 30.07, 0));
        modelCities.put("黄山" , new ModelCity("黄山", 118.337617, 29.714532, 0));
        modelCities.put("婺源" , new ModelCity("婺源", 117.83, 29.25, 0));
        modelCities.put("德兴" , new ModelCity("德兴", 117.58, 28.96, 0));
        modelCities.put("上饶" , new ModelCity("上饶", 117.97, 28.47, 0));
        modelCities.put("五府山" , new ModelCity("五府山", 118.052604, 28.139573, 0));
        modelCities.put("武夷山" , new ModelCity("武夷山", 118.035305, 27.756448, 0));
        modelCities.put("建阳" , new ModelCity("建阳", 118.120444, 27.331666, 0));
        modelCities.put("建瓯" , new ModelCity("建瓯", 118.32, 27.05, 0));
        modelCities.put("南平" , new ModelCity("南平", 118.16, 26.65, 0));
        modelCities.put("古田" , new ModelCity("古田", 118.746305, 26.57754, 0));
        modelCities.put("闽清" , new ModelCity("闽清", 118.86, 26.21, 0));
        modelCities.put("简阳" , new ModelCity("简阳", 104.53, 30.38, 0));
        modelCities.put("资阳" , new ModelCity("资阳", 104.6, 30.19, 0));
        modelCities.put("资中" , new ModelCity("资中", 104.85, 29.81, 0));
        modelCities.put("内江" , new ModelCity("内江", 105.04, 29.59, 0));
        modelCities.put("隆昌" , new ModelCity("隆昌", 105.25, 29.64, 0));
        modelCities.put("荣昌" , new ModelCity("荣昌", 105.580747, 29.393962, 0));
        modelCities.put("大足" , new ModelCity("大足", 105.740842, 29.427907, 0));
        modelCities.put("永川" , new ModelCity("永川", 105.900401, 29.340105, 0));
        modelCities.put("建始" , new ModelCity("建始", 109.726653, 30.601459, 0));
        modelCities.put("璧山" , new ModelCity("璧山", 106.227363, 29.591827, 0));
        modelCities.put("沙坪坝" , new ModelCity("沙坪坝", 106.457802, 29.540987, 0));
        modelCities.put("星火" , new ModelCity("星火", 116.508818, 39.947939, 0));
        modelCities.put("顺义" , new ModelCity("顺义", 116.65, 40.13, 0));
        modelCities.put("怀柔" , new ModelCity("怀柔", 116.62, 40.32, 0));
        modelCities.put("密云" , new ModelCity("密云", 116.85, 40.37, 0));
        modelCities.put("兴隆" , new ModelCity("兴隆", 117.500553, 40.417269, 0));
        modelCities.put("承德" , new ModelCity("承德", 117.962419, 40.953981, 0));
        modelCities.put("平泉" , new ModelCity("平泉", 118.6833333, 41.01666667, 0));
        modelCities.put("牛河梁" , new ModelCity("牛河梁", 119.509404, 41.295944, 0));
        modelCities.put("喀左" , new ModelCity("喀左", 119.741206, 41.128066, 0));
        modelCities.put("朝阳" , new ModelCity("朝阳", 120.42, 41.58, 0));
        modelCities.put("北票" , new ModelCity("北票", 120.75, 41.82, 0));
        modelCities.put("阜新" , new ModelCity("阜新", 121.670047, 42.022019, 0));
        modelCities.put("黑山" , new ModelCity("黑山", 122.12, 41.7, 0));
        modelCities.put("新民" , new ModelCity("新民", 122.828968, 41.997099, 0));
        modelCities.put("开封" , new ModelCity("开封", 114.35, 34.79, 0));
        modelCities.put("兰考" , new ModelCity("兰考", 114.81, 34.69, 0));
        modelCities.put("商丘" , new ModelCity("商丘", 115.65, 34.44, 0));
        modelCities.put("民权" , new ModelCity("民权", 115.13, 34.65, 0));
        modelCities.put("砀山" , new ModelCity("砀山", 116.34, 34.42, 0));
        modelCities.put("永城" , new ModelCity("永城", 116.37, 33.94, 0));
        modelCities.put("萧县" , new ModelCity("萧县", 116.93, 34.19, 0));
        modelCities.put("门源" , new ModelCity("门源", 101.62, 37.37, 0));
        modelCities.put("民乐" , new ModelCity("民乐", 100.85, 38.43, 0));
        modelCities.put("张掖" , new ModelCity("张掖", 100.46, 38.93, 0));
        modelCities.put("临泽" , new ModelCity("临泽", 100.17, 39.14, 0));
        modelCities.put("酒泉" , new ModelCity("酒泉", 98.5, 39.71, 0));
        modelCities.put("嘉峪关" , new ModelCity("嘉峪关", 98.289156, 39.773053, 0));
        modelCities.put("玉门" , new ModelCity("玉门", 97.58, 39.81, 0));
        modelCities.put("柳园" , new ModelCity("柳园", 95.506807, 41.106829, 0));
        modelCities.put("哈密" , new ModelCity("哈密", 93.44, 42.78, 0));
        modelCities.put("鄯善" , new ModelCity("鄯善", 90.25, 42.82, 0));
        modelCities.put("吐鲁番" , new ModelCity("吐鲁番", 89.19, 42.91, 0));
//        modelCities.put("贵定" , new ModelCity("贵定", 107.229787, 26.576133, 0));
        modelCities.put("龙里" , new ModelCity("龙里", 106.973816, 26.447054, 0));
        modelCities.put("都匀" , new ModelCity("都匀", 107.518728, 26.259305, 0));
        modelCities.put("从江" , new ModelCity("从江", 108.905221, 25.752776, 0));
        modelCities.put("榕江" , new ModelCity("榕江", 108.521875, 25.931797, 0));
        modelCities.put("三江" , new ModelCity("三江", 109.58, 25.8, 0));
        modelCities.put("桂林" , new ModelCity("桂林", 110.28, 25.29, 0));
        modelCities.put("恭城" , new ModelCity("恭城", 110.81, 24.85, 0));
        modelCities.put("贺州" , new ModelCity("贺州", 111.567196, 24.404103, 0));
        modelCities.put("怀集" , new ModelCity("怀集", 112.18, 23.93, 0));
        modelCities.put("肇庆" , new ModelCity("肇庆", 112.44, 23.05, 0));
        modelCities.put("三水" , new ModelCity("三水", 112.89, 23.18, 0));
        modelCities.put("大同" , new ModelCity("大同", 113.3, 40.12, 0));
        modelCities.put("怀仁" , new ModelCity("怀仁", 113.1, 39.82, 0));
        modelCities.put("岱岳" , new ModelCity("岱岳", 112.825173, 39.516807, 0));
        modelCities.put("朔州" , new ModelCity("朔州", 112.432852, 39.331379, 0));
        modelCities.put("宁武" , new ModelCity("宁武", 112.28, 39, 0));
        modelCities.put("原平" , new ModelCity("原平", 112.7, 38.73, 0));
        modelCities.put("忻州" , new ModelCity("忻州", 112.74829, 38.41806, 0));
        modelCities.put("榆次" , new ModelCity("榆次", 112.72, 37.68, 0));
        modelCities.put("太谷" , new ModelCity("太谷", 112.53, 37.42, 0));
        modelCities.put("平遥" , new ModelCity("平遥", 112.18, 37.2, 0));
        modelCities.put("介休" , new ModelCity("介休", 111.88, 37.03, 0));
        modelCities.put("临汾" , new ModelCity("临汾", 111.5, 36.08, 0));
        modelCities.put("侯马" , new ModelCity("侯马", 111.45, 35.03, 0));
        modelCities.put("运城" , new ModelCity("运城", 110.97, 35.03, 0));
        modelCities.put("永济" , new ModelCity("永济", 110.42, 34.88, 0));
        modelCities.put("华山" , new ModelCity("华山", 110.09, 34.58, 0));
        modelCities.put("渭南" , new ModelCity("渭南", 109.5, 34.52, 0));
        modelCities.put("成都" , new ModelCity("成都", 104.073951, 30.697235, 0));
        modelCities.put("安靖" , new ModelCity("安靖", 104.01354, 30.766973, 0));
        modelCities.put("金牛" , new ModelCity("金牛", 103.9904, 30.736269, 0));
        modelCities.put("犀浦" , new ModelCity("犀浦", 103.972097, 30.756002, 0));
        modelCities.put("红光" , new ModelCity("红光", 103.943859, 30.770605, 0));
        modelCities.put("郫县东" , new ModelCity("郫县东", 103.925942, 30.792174, 0));
        modelCities.put("郫县" , new ModelCity("郫县", 103.86, 30.8, 0));
        modelCities.put("郫县西" , new ModelCity("郫县西", 103.879979, 30.82343, 0));
        modelCities.put("安德" , new ModelCity("安德", 103.812055, 30.873907, 0));
        modelCities.put("崇义" , new ModelCity("崇义", 103.730219, 30.916871, 0));
        modelCities.put("聚源" , new ModelCity("聚源", 103.701022, 30.932529, 0));
        modelCities.put("都江堰" , new ModelCity("都江堰", 103.637787, 30.957005, 0));
        modelCities.put("中兴" , new ModelCity("中兴", 103.620441, 30.920203, 0));
        modelCities.put("青城山" , new ModelCity("青城山", 103.606249, 30.891767, 0));
        modelCities.put("九江" , new ModelCity("九江", 115.97, 29.71, 0));
        modelCities.put("庐山" , new ModelCity("庐山", 115.989048, 29.671667, 0));
        modelCities.put("德安" , new ModelCity("德安", 115.75, 29.33, 0));
        modelCities.put("共青城" , new ModelCity("共青城", 115.775496, 29.242148, 0));
        modelCities.put("永修" , new ModelCity("永修", 115.82, 29.04, 0));
        modelCities.put("乐化" , new ModelCity("乐化", 115.870553, 28.813958, 0));
        modelCities.put("榆中" , new ModelCity("榆中", 104.09, 35.87, 0));
        modelCities.put("定西" , new ModelCity("定西", 104.57, 35.57, 0));
        modelCities.put("通渭" , new ModelCity("通渭", 105.27, 35.24, 0));
        modelCities.put("秦安" , new ModelCity("秦安", 105.69, 34.89, 0));
        modelCities.put("天水" , new ModelCity("天水", 105.69, 34.6, 0));
        modelCities.put("东岔" , new ModelCity("东岔", 106.668594, 34.377418, 0));
//        modelCities.put("宝鸡" , new ModelCity("宝鸡", 107.15, 34.38, 0));
        modelCities.put("章丘" , new ModelCity("章丘", 117.53, 36.72, 0));
        modelCities.put("周村" , new ModelCity("周村", 117.860489, 36.791105, 0));
        modelCities.put("淄博" , new ModelCity("淄博", 118.05, 36.78, 0));
        modelCities.put("临淄" , new ModelCity("临淄", 118.056999, 36.789249, 0));
        modelCities.put("青州" , new ModelCity("青州", 118.484468, 36.708428, 0));
        modelCities.put("昌乐" , new ModelCity("昌乐", 118.83, 36.69, 0));
        modelCities.put("潍坊" , new ModelCity("潍坊", 119.1, 36.62, 0));
        modelCities.put("昌邑" , new ModelCity("昌邑", 119.41, 36.86, 0));
        modelCities.put("高密" , new ModelCity("高密", 119.75, 36.38, 0));
        modelCities.put("胶州" , new ModelCity("胶州", 120.033336, 36.264599, 0));
        modelCities.put("藁城" , new ModelCity("藁城", 114.84, 38.03, 0));
        modelCities.put("辛集" , new ModelCity("辛集", 115.213149, 37.9053, 0));
        modelCities.put("衡水" , new ModelCity("衡水", 115.690958, 37.744257, 0));
        modelCities.put("景州" , new ModelCity("景州", 116.270648, 37.692012, 0));
//        modelCities.put("德州" , new ModelCity("德州", 116.29, 37.45, 0));
        modelCities.put("平原" , new ModelCity("平原", 116.44, 37.16, 0));
        modelCities.put("禹城" , new ModelCity("禹城", 116.66, 36.95, 0));
        modelCities.put("齐河" , new ModelCity("齐河", 116.76, 36.79, 0));
        modelCities.put("凌井" , new ModelCity("凌井", 112.936301, 38.050474, 0));
        modelCities.put("阳泉" , new ModelCity("阳泉", 113.57, 37.85, 0));
        modelCities.put("井陉" , new ModelCity("井陉", 114.13, 38.03, 0));
//        modelCities.put("厦门" , new ModelCity("厦门", 118.1, 24.46, 0));
        modelCities.put("角美" , new ModelCity("角美", 117.876103, 24.523993, 0));
        modelCities.put("漳州" , new ModelCity("漳州", 117.716015, 24.456773, 0));
        modelCities.put("漳浦" , new ModelCity("漳浦", 117.563145, 24.096132, 0));
        modelCities.put("云霄" , new ModelCity("云霄", 117.339556, 23.957836, 0));
        modelCities.put("诏安" , new ModelCity("诏安", 117.175180, 23.711371, 0));
        modelCities.put("饶平" , new ModelCity("饶平", 117.003982, 23.663863, 0));
        modelCities.put("潮汕" , new ModelCity("潮汕", 116.587135, 23.539681, 0));
        modelCities.put("潮阳" , new ModelCity("潮阳", 116.601391, 23.265130, 0));
        modelCities.put("普宁" , new ModelCity("普宁", 116.199188, 23.266983, 0));
        modelCities.put("葵潭" , new ModelCity("葵潭", 115.996872, 23.064112, 0));
        modelCities.put("陆丰" , new ModelCity("陆丰", 115.652286, 22.917496, 0));
        modelCities.put("汕尾" , new ModelCity("汕尾", 115.4287, 22.810806, 0));
        modelCities.put("鲘门" , new ModelCity("鲘门", 115.125108, 22.812218, 0));
        modelCities.put("惠东" , new ModelCity("惠东", 114.720183, 22.984924, 0));
        modelCities.put("惠州" , new ModelCity("惠州", 114.416104, 23.111686, 0));
//        modelCities.put("深圳" , new ModelCity("深圳", 114.07, 22.62, 0));
        modelCities.put("本溪新城" , new ModelCity("本溪新城", 123.681859, 41.493084, 0));
        modelCities.put("本溪" , new ModelCity("本溪", 123.73, 41.3, 0));
        modelCities.put("南芬" , new ModelCity("南芬", 123.749338, 41.102486, 0));
        modelCities.put("通远堡" , new ModelCity("通远堡", 123.927051, 40.786887, 0));
        modelCities.put("凤城" , new ModelCity("凤城", 124.05, 40.47, 0));
        modelCities.put("五龙背" , new ModelCity("五龙背", 124.275293, 40.259806, 0));
        modelCities.put("绍兴" , new ModelCity("绍兴", 120.58, 30.01, 0));
        modelCities.put("上虞" , new ModelCity("上虞", 120.87, 30.03, 0));
        modelCities.put("余姚" , new ModelCity("余姚", 121.16, 30.04, 0));
        modelCities.put("庄桥" , new ModelCity("庄桥", 121.536071, 29.92323, 0));
        modelCities.put("宁波" , new ModelCity("宁波", 121.56, 29.86, 0));

    }
    private UCity() {

    }

    public static ModelCity getCity(String name) {
        return modelCities.get(name);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        Set<String> keys = modelCities.keySet();
        for (String key : keys) {
            System.out.println(modelCities.get(key));
        }
    }
}
