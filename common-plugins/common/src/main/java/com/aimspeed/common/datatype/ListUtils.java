package com.aimspeed.common.datatype;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 在数据库中查出来的列表中，往往需要对不同的字段重新排序。 一般的做法都是使用排序的字段，重新到数据库中查询。
 * 如果不到数据库查询，直接在第一次查出来的list中排序，无疑会提高系统的性能。 下面就写一个通用的方法，对list排序，
 *
 * 至少需要满足以下5点：
 *
 * ①.list元素对象类型任意
 * ---->使用泛型解决
 *
 * ②.可以按照list元素对象的任意多个属性进行排序,即可以同时指定多个属性进行排序
 * --->使用java的可变参数解决
 *
 * ③.list元素对象属性的类型可以是数字(byte、short、int、long、float、double等，包括正数、负数、0)、字符串(char、String)、日期(java.util.Date)
 * --->对于数字：统一转换为固定长度的字符串解决,比如数字3和123，转换为"003"和"123" ;再比如"-15"和"7"转换为"-015"和"007"
 * --->对于日期：可以先把日期转化为long类型的数字，数字的解决方法如上
 *
 * ④.list元素对象的属性可以没有相应的getter和setter方法
 * --->可以使用java反射进行获取private和protected修饰的属性值
 *
 * ⑤.list元素对象的对象的每个属性都可以指定是升序还是降序
 * -->使用2个重写的方法(一个方法满足所有属性都按照升序(降序)，另外一个方法满足每个属性都能指定是升序(降序))
 *
 * @author AimSpeed
*/
public class ListUtils {
	
	 public static void main(String[] args) throws Exception {
		 //================ 测试

		 List<UserInfo> list = new ArrayList<UserInfo>();
		 // public UserInfo(Integer userId, String username, Date birthDate,Integer age, float fRate, char ch)
		 SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		 UserInfo user1 = new UserInfo(3, "bbb", formater.parse("1980-12-01"), 1, 1.2f, 'a');
		 UserInfo user2 = new UserInfo(0, "126", formater.parse("1900-10-11"), 03, -3.6f, 'c');
		 UserInfo user3 = new UserInfo(12, "5", formater.parse("1973-08-21"), 15, 9.32f, 'f');
		 UserInfo user4=new UserInfo(465,"1567",formater.parse("2012-01-26"),20,12.56f,'0');
		 UserInfo user5=new UserInfo(2006,"&4m",formater.parse("2010-05-08"),100,165.32f,'5');
		 UserInfo user6=new UserInfo(5487,"hf67",formater.parse("2016-12-30"),103,56.32f,'m');
		 UserInfo user7=new UserInfo(5487,"jigg",formater.parse("2000-10-16"),103,56.32f,'m');
		 UserInfo user8=new UserInfo(5487,"jigg",formater.parse("1987-07-25"),103,56.32f,'m');
		
		 list.add(user1);
		 list.add(user2);
		 list.add(user3);
		 list.add(user4);
		 list.add(user5);
		 list.add(user6);
		 list.add(user7);
		 list.add(user8);
		
		 /*ystem.out.println("\n-------原来序列-------------------");
		 testObj.printfUserInfoList(list);
		
		 //按userId升序、username降序、birthDate升序排序
		 String[]sortNameArr={"userId","username","birthDate"};
		 boolean[]isAscArr={true,false,true};
		 ListUtils.sort(list,sortNameArr,isAscArr);
		 System.out.println("\n--------按按userId升序、username降序、birthDate升序排序（如果userId相同，则按照username降序,如果username相同，则按照birthDate升序）------------------");
		 testObj.printfUserInfoList(list);
		*/
		 //按userId、username、birthDate都升序排序
		 ListUtils.sort(list,true,"userId","username","birthDate");
		 System.out.println("\n--------按userId、username、birthDate排序（如果userId相同，则按照username升序,如果username相同，则按照birthDate升序）------------------");
		 for(UserInfo user : list){
			 System.out.println(user.toString());
		 }
		 /*
		 //按userId、username都倒序排序
		 ListUtils.sort(list,false,"userId","username");
		 System.out.println("\n--------按userId和username倒序（如果userId相同，则按照username倒序）------------------");
		 testObj.printfUserInfoList(list);
		
		 //按username、birthDate都升序排序
		 ListUtils.sort(list,true,"username","birthDate");
		 System.out.println("\n---------按username、birthDate升序（如果username相同，则按照birthDate升序）-----------------");
		 testObj.printfUserInfoList(list);
		
		 //按birthDate倒序排序
		 ListUtils.sort(list,false,"birthDate");
		 System.out.println("\n---------按birthDate倒序-----------------");
		 testObj.printfUserInfoList(list);
		
		 //按fRate升序排序
		 ListUtils.sort(list,true,"fRate");
		 System.out.println("\n---------按fRate升序-----------------");
		 testObj.printfUserInfoList(list);
		
		 //按ch倒序排序
		 ListUtils.sort(list,false,"ch");
		 System.out.println("\n---------按ch倒序-----------------");
		 testObj.printfUserInfoList(list);*/
		
	}
	
	/**
	 * 对list的元素按照多个属性名称排序,
	 * list元素的属性可以是数字（byte、short、int、long、float、double等，支持正数、负数、0）、char、String、java.util.Date
     * @author AimSpeed
	 * @param lsit
	 * @param sortname
	 * list元素的属性名称
	 * @param isAsc
	 * true升序，false降序
	 */
	public static <E> void sort(List<E> list, final boolean isAsc, final String... sortnameArr) {
		Collections.sort(list, new Comparator<E>() {

			public int compare(E a, E b) {
				int ret = 0;
				try {
					for (int i = 0; i < sortnameArr.length; i++) {
						ret = ListUtils.compareObject(sortnameArr[i], isAsc, a, b);
						if (0 != ret) {
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ret;
			}
		});
	}

	/**
	 * 给list的每个属性都指定是升序还是降序
     * @author AimSpeed
	 * @param list
	 * @param sortnameArr 参数数组
	 * @param typeArr 每个属性对应的升降序数组， true升序，false降序
	 */

	public static <E> void sort(List<E> list, final String[] sortnameArr, final boolean[] typeArr) {
		 if (sortnameArr.length != typeArr.length) {
			 throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
		 }
		 Collections.sort(list, new Comparator<E>() {
			 public int compare(E a, E b) {
				 int ret = 0;
				 try {
					 for (int i = 0; i < sortnameArr.length; i++) {
						 ret = ListUtils.compareObject(sortnameArr[i], typeArr[i], a, b);
						 if (0 != ret) {
							 break;
						 }
					 }
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
				 return ret;
			 }
		 });
	 }

	 /**
	  * 对2个对象按照指定属性名称进行排序
      * @author AimSpeed
	  * @param sortname 属性名称
	  * @param isAsc true升序，false降序
	  * @param a
	  * @param b
	  * @return
	  * @throws Exception
	  */
	 private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b) throws Exception {
		 int ret;
		 Object value1 = ListUtils.forceGetFieldValue(a, sortname);
		 Object value2 = ListUtils.forceGetFieldValue(b, sortname);
		 String str1 = value1.toString();
		 String str2 = value2.toString();
		 
		 if (value1 instanceof Number && value2 instanceof Number) {
			 int maxlen = Math.max(str1.length(), str2.length());
			 str1 = ListUtils.addZero2Str((Number) value1, maxlen);
			 str2 = ListUtils.addZero2Str((Number) value2, maxlen);
		 } else if (value1 instanceof Date && value2 instanceof Date) {
			 long time1 = ((Date) value1).getTime();
			 long time2 = ((Date) value2).getTime();
			 int maxlen = Long.toString(Math.max(time1, time2)).length();
			 str1 = ListUtils.addZero2Str(time1, maxlen);
			 str2 = ListUtils.addZero2Str(time2, maxlen);
		 }
		 
		 if (isAsc) {
			 ret = str1.compareTo(str2);
		 } else {
			 ret = str2.compareTo(str1);
		 }
		 return ret;
	 }

	 /**
	  * 给数字对象按照指定长度在左侧补0.
	  *
	  * 使用案例: addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
      * @author AimSpeed
	  * @param numObj 数字对象
	  * @param length 指定的长度
	  * @return
	  */
	 public static String addZero2Str(Number numObj, int length) {
		 NumberFormat nf = NumberFormat.getInstance();
		 // 设置是否使用分组
		 nf.setGroupingUsed(false);
		 // 设置最大整数位数
		 nf.setMaximumIntegerDigits(length);
		 // 设置最小整数位数
		 nf.setMinimumIntegerDigits(length);
		 return nf.format(numObj);
	 }



	 /**
	  * 获取指定对象的指定属性值（去除private,protected的限制）
      * @author AimSpeed
	  * @param obj 属性名称所在的对象
	  * @param fieldName 属性名称
	  * @return
	  * @throws Exception
	  */
	 public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
		 Field field = obj.getClass().getDeclaredField(fieldName);
		 Object object = null;
		 boolean accessible = field.isAccessible();
		 if (!accessible) {
			 // 如果是private,protected修饰的属性，需要修改为可以访问的
			 field.setAccessible(true);
			 object = field.get(obj);
			 // 还原private,protected属性的访问性质
			 field.setAccessible(accessible);
			 return object;
		 }
		 object = field.get(obj);
		 return object;
	 }
}

//=========================== 测试类 ==================================
class UserInfo {
	private static final long serialVersionUID = -3522051445403971732L;

	private Integer userId;
	private String username;
	private Date birthDate;
	private Integer age;
	private float fRate;
	private char ch;

	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public float getfRate() {
		return fRate;
	}

	public void setfRate(float fRate) {
		this.fRate = fRate;
	}

	public char getCh() {
		return ch;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public String getBirthDatestr() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		return formater.format(getBirthDate());
	}


	public UserInfo(Integer userId, String username, Date birthDate, Integer age, float fRate, char ch) {
		super();
		this.userId = userId;
		this.username = username;
		this.birthDate = birthDate;
		this.age = age;
		this.fRate = fRate;
		this.ch = ch;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", username=" + username + ", birthDate=" + birthDate + ", age=" + age
				+ ", fRate=" + fRate + ", ch=" + ch + "]";
	}
	
	
}
